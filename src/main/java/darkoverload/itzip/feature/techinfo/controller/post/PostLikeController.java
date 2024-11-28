package darkoverload.itzip.feature.techinfo.controller.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.service.like.LikeService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Tech Info Post Like",
        description = "기술 정보 게시글 좋아요 토글 기능을 제공하는 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info/post")
public class PostLikeController {

    private final LikeService likeService;

    @Operation(
            summary = "게시글 좋아요 토글",
            description = "특정 기술 게시글에 대한 좋아요를 추가하거나 취소합니다."
    )
    @ExceptionCodeAnnotations({CommonExceptionCode.NOT_FOUND_USER})
    @PostMapping("/{postId}/like")
    public String togglePostLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "포스트 ID", example = "66e724e50000000000db4e53")
            @PathVariable @NotBlank String postId
    ) {
        return likeService.toggleLike(userDetails, postId) ? "게시글에 좋아요를 추가했습니다." : "게시글의 좋아요를 취소했습니다.";
    }

}
