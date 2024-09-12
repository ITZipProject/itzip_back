package darkoverload.itzip.feature.user.controller;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.controller.request.ChangeNicknameRequest;
import darkoverload.itzip.feature.user.controller.request.ChangePasswordRequest;
import darkoverload.itzip.feature.user.service.MypageService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mypage", description = "마이페이지 기능 API")
@RestController
@RequestMapping("/mypage")
@Slf4j
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    /**
     * 닉네임 중복 체크
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
        return mypageService.checkDuplicateNickname(nickname);
    }

    /**
     * 닉네임 변경 기능
     */
    @Operation(
            summary = "닉네임 변경 기능",
            description = "유저 정보 수정 시 닉네임을 변경합니다."
    )
    @PatchMapping("/nickname")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR, CommonExceptionCode.EXIST_NICKNAME_ERROR, CommonExceptionCode.NOT_FOUND_USER})
    public ResponseEntity<String> changeNickname(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid ChangeNicknameRequest request,
            BindingResult bindingResult
    ) {
        return mypageService.changeNickname(userDetails, request, bindingResult);
    }

    /**
     * 비밀번호 변경 기능
     */
    @Operation(
            summary = "비밀번호 변경 기능",
            description = "유저 정보 수정 시 비밀번호를 변경합니다."
    )
    @PatchMapping("/password")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR, CommonExceptionCode.NOT_FOUND_USER})
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid ChangePasswordRequest request,
            BindingResult bindingResult
    ) {
        return mypageService.changePassword(userDetails, request, bindingResult);
    }
}
