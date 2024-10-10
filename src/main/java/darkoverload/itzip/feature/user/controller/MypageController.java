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
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @Parameter(description = "사용할 닉네임") @RequestParam @NotBlank String nickname
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
            @RequestBody @Valid ChangeNicknameRequest changeNicknameRequest
    ) {
        return mypageService.changeNickname(userDetails, changeNicknameRequest);
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
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest
    ) {
        return mypageService.changePassword(userDetails, changePasswordRequest);
    }

    /**
     * 프로필 이미지 변경 기능
     */
    @Operation(
            summary = "프로필 이미지 변경 기능",
            description = "유저 정보 수정 시 프로필 이미지를 변경합니다."
    )
    @PatchMapping(value = "/profileImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR, CommonExceptionCode.NOT_FOUND_USER})
    public ResponseEntity<String> changeImageUrl(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("file") MultipartFile file) {
        return mypageService.changeImageUrl(userDetails, file);
    }
}
