package darkoverload.itzip.feature.user.controller;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.controller.request.*;
import darkoverload.itzip.feature.user.controller.response.UserInfoResponse;
import darkoverload.itzip.feature.user.controller.response.UserLoginResponse;
import darkoverload.itzip.feature.user.service.UserService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
     * 로그인 중인 유저 정보 반환
     */
    @Operation(
            summary = "로그인 유저 정보",
            description = "현재 로그인 되어있는 유저의 정보를 반환합니다."
    )
    @GetMapping
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_USER)
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getUserInfo(userDetails);
    }

    /**
     * 로그인
     */
    @Operation(
            summary = "이메일 로그인",
            description = "이메일과 비밀번호를 입력받아 jwt 토큰을 발급합니다."
    )
    @PostMapping("/login")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR, CommonExceptionCode.NOT_MATCH_PASSWORD})
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        return userService.login(request, bindingResult, httpServletResponse);
    }

    /**
     * 로그아웃
     */
    @Operation(
            summary = "로그아웃",
            description = "저장된 jwt 토큰을 제거합니다."
    )
    @DeleteMapping("/logout")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        return userService.logout(request, response);
    }

    /**
     * 리프레시 토큰으로 엑세스 토큰 갱신
     */
    @Operation(
            summary = "엑세스 토큰 재발급",
            description = "저장된 리프레시 토큰으로 엑세스 토큰을 재발급합니다."
    )
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
    @Operation(
            summary = "이메일 회원가입",
            description = "회원 데이터를 추가합니다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.CREATED)
    @ExceptionCodeAnnotations(CommonExceptionCode.FILED_ERROR)
    public String join(@RequestBody @Valid UserJoinRequest request, BindingResult bindingResult) {
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
    public String sendAuthEmail(@RequestBody @Valid AuthEmailSendRequest request, BindingResult bindingResult) {
        return userService.sendAuthEmail(request, bindingResult);
    }

    /**
     * 인증번호 검증
     */
    @Operation(
            summary = "인증번호 검증",
            description = "입력받은 인증번호와 서버에 저장된 인증번호를 대조하여 이메일을 인증 처리 합니다."
    )
    @GetMapping("/authEmail")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR, CommonExceptionCode.NOT_MATCH_AUTH_CODE})
    public String checkAuthEmail(
            @Parameter(description = "이메일") @RequestParam(required = false) String email,
            @Parameter(description = "이메일 인증 코드") @RequestParam(required = false) String authCode
    ) {
        return userService.checkAuthEmail(email, authCode);
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
    public String checkDuplicateEmail(
            @Parameter(description = "이메일") @RequestParam(required = false) String email
    ) {
        return userService.checkDuplicateEmail(email);
    }

    /**
     * 임시 유저 탈퇴 메소드
     */
    @Operation(
            summary = "임시 유저 탈퇴",
            description = "현재 로그인 되어 있는 유저 데이터를 삭제합니다."
    )
    @DeleteMapping("/out")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR, CommonExceptionCode.NOT_FOUND_USER})
    public String tempUserOut(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return userService.tempUserOut(userDetails, request, response);
    }
}
