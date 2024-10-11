package darkoverload.itzip.feature.techinfo.controller.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.service.like.LikeService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.constraints.NotBlank;

import lombok.RequiredArgsConstructor;

@Tag(
        name = "Tech Info Post Like",
        description = "포스트에 대한 좋아요 기능을 제공하는 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info")
public class TechInfoLikeController {

    private final LikeService likeService;

    @Operation(
            summary = "좋아요 상태 변경",
            description = "특정 포스트에 대한 좋아요를 추가하거나 취소한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_USER)
    @PostMapping("like/toggle")
    public String toggleLikeStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "포스트 ID", example = "66e724e50000000000db4e53") @RequestParam @NotBlank String postId)
    {
        boolean isLiked = likeService.toggleLike(userDetails, postId);
        return isLiked ? "좋아요가 추가되었습니다." : "좋아요가 취소되었습니다.";
    }
}