package darkoverload.itzip.user.controller;

import darkoverload.itzip.jwt.entity.Token;
import darkoverload.itzip.jwt.service.TokenService;
import darkoverload.itzip.jwt.util.JwtTokenizer;
import darkoverload.itzip.user.dto.UserLoginDto;
import darkoverload.itzip.user.dto.UserLoginResponseDto;
import darkoverload.itzip.user.entity.Authority;
import darkoverload.itzip.user.entity.User;
import darkoverload.itzip.user.sevice.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserLoginDto userLoginDto, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        // 필드 에러 확인
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            Map<String, String> errors = fieldErrors.stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            return new ResponseEntity(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByEmail(userLoginDto.getEmail());

        // 비밀번호 일치여부 체크
        if (user == null || !passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return new ResponseEntity(
                    Map.of("errors", Map.of("password", "이메일과 비밀번호가 일치하지 않습니다.")),
                    HttpStatus.UNAUTHORIZED
            );
        }

        // 토큰 발급
        String accessToken = jwtTokenizer.createAccessToken(user.getId(), user.getEmail(), user.getNickname(), user.getAuthority());
        String refreshToken = jwtTokenizer.createRefreshToken(user.getId(), user.getEmail(), user.getNickname(), user.getAuthority());

        // 리프레시 토큰 디비 저장
        Token token = Token.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType("Bearer")
                .build();

        tokenService.saveOrUpdate(token);

        // 토큰 쿠키 저장
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.accessTokenExpire / 1000));
        httpServletResponse.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.refreshTokenExpire / 1000));
        httpServletResponse.addCookie(refreshTokenCookie);

        // 응답 값
        UserLoginResponseDto loginResponseDto = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();

        return new ResponseEntity(loginResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = null;

        // access / refresh token cookie 삭제
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "accessToken":
                        accessToken = cookie.getValue();
                    case "refreshToken":
                        cookie.setValue("");
                        cookie.setPath("/");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                        break;
                }
            }
        }

        // tokens 데이터 삭제
        tokenService.deleteByAccessToken(accessToken);
        return new ResponseEntity("로그아웃 되었습니다.", HttpStatus.OK);
    }

    @PatchMapping("/refreshToken")
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // refresh token cookie 가져오기
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        // refresh token이 없을 경우
        if (refreshToken == null) {
            return new ResponseEntity("토큰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // refresh token 파싱
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);

        // 유저 정보 가져오기
        Long userId = Long.valueOf((Integer) claims.get("userId"));
        User user = userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾지 못했습니다."));
        Authority authority = Authority.valueOf(claims.get("authority", String.class));

        // access token 생성
        String accessToken = jwtTokenizer.createAccessToken(userId, user.getEmail(), user.getNickname(), authority);

        // accessToken 쿠키 생성
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.accessTokenExpire / 1000));

        response.addCookie(accessTokenCookie);

        // Token 데이터 access token 값 업데이트
        tokenService.updateByRefreshToken(refreshToken, accessToken);

        // 응답 값
        UserLoginResponseDto responseDto = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(user.getNickname())
                .userId(user.getId())
                .build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
}
