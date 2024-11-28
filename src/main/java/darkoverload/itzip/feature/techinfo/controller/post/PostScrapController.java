package darkoverload.itzip.feature.techinfo.controller.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.service.scrap.ScrapService;
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
        name = "Tech Info Post Scrap",
        description = "기술 정보 게시글 스크랩 토글 기능을 제공하는 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info/post")
public class PostScrapController {

    private final ScrapService scrapService;

    @Operation(
            summary = "게시글 스크랩 토글",
            description = "특정 기술 정보 포스트에 대한 스크랩을 추가하거나 취소합니다."
    )
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_USER)
    @PostMapping("/{postId}/scrap")
    public String toggleScrapStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "포스트 ID", example = "66e724e50000000000db4e53")
            @PathVariable @NotBlank String postId
    ) {
        boolean isScrapped = scrapService.toggleScrap(userDetails, postId);
        return isScrapped ? "게시글을 스크랩했습니다." : "게시글의 스크랩을 취소했습니다.";
    }

}
