package darkoverload.itzip.feature.user.controller;

import darkoverload.itzip.feature.user.controller.request.*;
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
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
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
    @Operation(
            summary = "인증번호 검증",
            description = "입력받은 인증번호와 서버에 저장된 인증번호를 대조하여 이메일을 인증 처리 합니다."
    )
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

    /**
     * 이메일 중복 체크 메소드
     */
    @Operation(
            summary = "닉네임 중복 체크",
            description = "유저 정보 수정 시 중복된 닉네임인지 확인합니다."
    )
    @GetMapping("/checkDuplicateNickname")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR})
    public ResponseEntity<String> checkDuplicateNickname(
            @Parameter(description = "사용할 닉네임") @RequestParam(required = false) String nickname
    ) {
        return userService.checkDuplicateNickname(nickname);
    }
}
