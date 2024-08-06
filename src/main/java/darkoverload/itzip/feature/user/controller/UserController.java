package darkoverload.itzip.feature.user.controller;

import darkoverload.itzip.feature.jwt.domain.Token;
import darkoverload.itzip.feature.jwt.service.TokenService;
import darkoverload.itzip.feature.jwt.util.JwtTokenizer;
import darkoverload.itzip.feature.user.controller.request.EmailCheckRequest;
import darkoverload.itzip.feature.user.controller.request.EmailSendRequest;
import darkoverload.itzip.feature.user.controller.request.UserJoinRequest;
import darkoverload.itzip.feature.user.controller.request.UserLoginRequest;
import darkoverload.itzip.feature.user.controller.response.UserLoginResponse;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.Authority;
import darkoverload.itzip.feature.user.service.EmailService;
import darkoverload.itzip.feature.user.service.UserService;
import darkoverload.itzip.feature.user.service.VerificationService;
import darkoverload.itzip.feature.user.util.RandomAuthCode;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
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

    @PostMapping("/login")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR, CommonExceptionCode.NOT_MATCH_PASSWORD})
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        // 필드 에러 확인
        if (bindingResult.hasErrors()) {
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }

        User user = userService.findByEmail(userLoginRequest.getEmail()).orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_MATCH_PASSWORD));

        // 비밀번호 일치여부 체크
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new RestApiException(CommonExceptionCode.NOT_MATCH_PASSWORD);
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

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
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
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @PatchMapping("/refreshToken")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.JWT_UNKNOWN_ERROR, CommonExceptionCode.NOT_FOUND_USER})
    public ResponseEntity<UserLoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
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
            throw new RestApiException(CommonExceptionCode.JWT_UNKNOWN_ERROR);
        }

        // refresh token 파싱
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);

        // 유저 정보 가져오기
        Long userId = Long.valueOf((Integer) claims.get("userId"));
        User user = userService.findById(userId).orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));
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
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(user.getNickname())
                .userId(user.getId())
                .build();

        return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
    }

    @PostMapping("/join")
    @ResponseCodeAnnotation(CommonResponseCode.CREATED)
    @ExceptionCodeAnnotations(CommonExceptionCode.FILED_ERROR)
    public ResponseEntity<String> join(@RequestBody @Valid UserJoinRequest userJoinDto, BindingResult bindingResult) {
        // 필드 에러 확인
        if (bindingResult.hasErrors()) {
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }

        userService.save(userJoinDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    /**
     * 인증번호 발송 메소드
     */
    @PostMapping("/authEmail")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.FILED_ERROR)
    public ResponseEntity<String> sendAuthEmail(@RequestBody @Valid EmailSendRequest emailSendDto, BindingResult bindingResult) {
        // 필드 에러 확인
        if (bindingResult.hasErrors()) {
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
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
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR, CommonExceptionCode.NOT_MATCH_AUTH_CODE})
    public ResponseEntity<String> checkAuthEmail(@RequestBody @Valid EmailCheckRequest emailCheckDto, BindingResult bindingResult) {
        // 필드 에러 확인
        if (bindingResult.hasErrors()) {
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }

        // redis에 저장된 인증번호와 비교하여 확인
        if (!verificationService.verifyCode(emailCheckDto.getEmail(), emailCheckDto.getAuthCode())) {
            throw new RestApiException(CommonExceptionCode.NOT_MATCH_AUTH_CODE);
        }

        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}
