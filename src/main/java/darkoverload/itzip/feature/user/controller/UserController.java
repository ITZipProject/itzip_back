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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원 기능 API")
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
@Validated
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
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        return userService.login(userLoginRequest);
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
    public String logout(HttpServletRequest request) {
        return userService.logout(request);
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
    @ExceptionCodeAnnotations(CommonExceptionCode.FILED_ERROR)
    public ResponseEntity<UserLoginResponse> refreshAccessToken(@RequestBody @Valid RefreshAccessTokenRequest refreshAccessTokenRequest) {
        return userService.refreshAccessToken(refreshAccessTokenRequest);
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
    public String join(@RequestBody @Valid UserJoinRequest userJoinRequest) {
        return userService.save(userJoinRequest);
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
    public String sendAuthEmail(@RequestBody @Valid AuthEmailSendRequest authEmailSendRequest) {
        return userService.sendAuthEmail(authEmailSendRequest);
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
            @Parameter(description = "이메일") @RequestParam @NotBlank @Email String email,
            @Parameter(description = "이메일 인증 코드") @RequestParam @NotBlank String authCode
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
            @Parameter(description = "이메일") @RequestParam @NotBlank @Email String email
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
            HttpServletRequest request
    ) {
        return userService.tempUserOut(userDetails, request);
    }

    /**
     * 비밀번호 재설정 요청 메소드
     */
    @Operation(
            summary = "비밀번호 재설정 요청",
            description = "비밀번호 재설정 메일을 발송합니다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.INTERNAL_SERVER_ERROR, CommonExceptionCode.NOT_FOUND_USER})
    @PostMapping("/passwordReset")
    public String requestPasswordReset(HttpServletRequest request,
                                       @RequestBody @Valid PasswordResetRequest passwordResetRequest) {
        return userService.requestPasswordReset(passwordResetRequest, request);
    }

    /**
     * 비밀번호 재설정 승인 메소드
     */
    @Operation(
            summary = "비밀번호 재설정 승인",
            description = "비밀번호 재설정 후 메인페이지로 이동합니다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.INTERNAL_SERVER_ERROR, CommonExceptionCode.NOT_FOUND_USER})
    @GetMapping("/passwordReset")
    public void confirmPasswordReset(HttpServletResponse response,
                                     @Parameter(description = "유저 정보를 포함한 토큰값") @RequestParam @NotBlank String token) {
        userService.confirmPasswordReset(response, token);
    }
}
