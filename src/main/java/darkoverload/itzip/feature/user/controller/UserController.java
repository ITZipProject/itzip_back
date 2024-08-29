package darkoverload.itzip.feature.user.controller;

import darkoverload.itzip.feature.user.controller.request.*;
import darkoverload.itzip.feature.user.controller.response.UserLoginResponse;
import darkoverload.itzip.feature.user.service.UserService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원 기능 API")
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 로그인
     */
    @PostMapping("/login")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR, CommonExceptionCode.NOT_MATCH_PASSWORD})
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        return userService.login(request, bindingResult, httpServletResponse);
    }

    /**
     * 로그아웃
     */
    @DeleteMapping("/logout")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return userService.logout(request, response);
    }

    /**
     * 리프레시 토큰으로 엑세스 토큰 갱신
     */
    @PatchMapping("/refreshToken")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.JWT_UNKNOWN_ERROR, CommonExceptionCode.NOT_FOUND_USER})
    public ResponseEntity<UserLoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return userService.refreshToken(request, response);
    }

    /**
     * 회원가입
     */
    @PostMapping("/join")
    @ResponseCodeAnnotation(CommonResponseCode.CREATED)
    @ExceptionCodeAnnotations(CommonExceptionCode.FILED_ERROR)
    public ResponseEntity<String> join(@RequestBody @Valid UserJoinRequest request, BindingResult bindingResult) {
        return userService.save(request, bindingResult);
    }

    /**
     * 인증번호 발송
     */
    @Operation(
            summary = "인증메일 발송",
            description = "회원가입 시 인증메일을 발송합니다."
    )
    @PostMapping("/authEmail")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.FILED_ERROR)
    public ResponseEntity<String> sendAuthEmail(@RequestBody @Valid AuthEmailSendRequest request, BindingResult bindingResult) {
        return userService.sendAuthEmail(request, bindingResult);
    }

    /**
     * 인증번호 검증
     */
    @GetMapping("/authEmail")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR, CommonExceptionCode.NOT_MATCH_AUTH_CODE})
    public ResponseEntity<String> checkAuthEmail(@RequestBody @Valid AuthEmailCheckRequest request, BindingResult bindingResult) {
        return userService.checkAuthEmail(request, bindingResult);
    }

    /**
     * 이메일 중복 체크 메소드
     */
    @Operation(
            summary = "이메일 중복 체크",
            description = "회원가입 시 중복된 이메일인지 확인합니다."
    )
    @GetMapping("/checkDuplicateEmail")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR})
    public ResponseEntity<String> checkDuplicateEmail(@RequestBody @Valid DuplicateEmailRequest request, BindingResult bindingResult) {
        return userService.checkDuplicateEmail(request, bindingResult);
    }
}
