package darkoverload.itzip.user.controller;

import darkoverload.itzip.jwt.domain.Token;
import darkoverload.itzip.jwt.service.TokenService;
import darkoverload.itzip.jwt.util.JwtTokenizer;
import darkoverload.itzip.user.controller.request.EmailCheckRequest;
import darkoverload.itzip.user.controller.request.EmailSendRequest;
import darkoverload.itzip.user.controller.request.UserJoinRequest;
import darkoverload.itzip.user.controller.request.UserLoginRequest;
import darkoverload.itzip.user.controller.response.UserLoginResponse;
import darkoverload.itzip.user.domain.User;
import darkoverload.itzip.user.entity.Authority;
import darkoverload.itzip.user.service.EmailService;
import darkoverload.itzip.user.service.UserService;
import darkoverload.itzip.user.service.VerificationService;
import darkoverload.itzip.user.util.RandomAuthCode;
import darkoverload.itzip.user.util.ValidationUtil;
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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final VerificationService verificationService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final ValidationUtil validationUtil;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserLoginRequest userLoginDto, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        // 필드 에러 확인
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = validationUtil.getBindingError(bindingResult);

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
        UserLoginResponse loginResponseDto = UserLoginResponse.builder()
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
        UserLoginResponse responseDto = UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(user.getNickname())
                .userId(user.getId())
                .build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody @Valid UserJoinRequest userJoinDto, BindingResult bindingResult) {
        // 필드 에러 확인
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = validationUtil.getBindingError(bindingResult);

            return new ResponseEntity(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
        }

        userService.save(userJoinDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    /**
     * 인증번호 발송 메소드
     */
    @PostMapping("/authEmail")
    public ResponseEntity sendAuthEmail(@RequestBody @Valid EmailSendRequest emailSendDto, BindingResult bindingResult) {
        // 필드 에러 확인
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = validationUtil.getBindingError(bindingResult);

            return new ResponseEntity(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
        }

        // 랜덤 인증 코드 생성
        String authCode = RandomAuthCode.generate();
        // redis에 인증 코드 저장
        verificationService.saveCode(emailSendDto.getEmail(), authCode);
        // 메일 발송
        emailService.sendSimpleMessage(emailSendDto.getEmail(), "test", authCode);
        return ResponseEntity.ok("인증 메일이 발송되었습니다.");
    }

    /**
     * 인증번호 검증 메소드
     */
    @GetMapping("/authEmail")
    public ResponseEntity checkAuthEmail(@RequestBody @Valid EmailCheckRequest emailCheckDto, BindingResult bindingResult) {
        // 필드 에러 확인
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = validationUtil.getBindingError(bindingResult);

            return new ResponseEntity(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
        }

        // redis에 저장된 인증번호와 비교하여 확인
        boolean isVerified = verificationService.verifyCode(emailCheckDto.getEmail(), emailCheckDto.getAuthCode());

        return ResponseEntity.ok(isVerified ? "인증이 완료되었습니다." : "인증번호를 확인해주세요.");
    }
}
