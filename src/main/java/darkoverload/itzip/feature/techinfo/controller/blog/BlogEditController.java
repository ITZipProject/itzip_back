package darkoverload.itzip.feature.techinfo.controller.blog;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.request.BlogUpdateIntroRequest;
import darkoverload.itzip.feature.techinfo.service.blog.BlogCommandService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Tech Info Blog",
        description = "기술 정보 블로그 수정 및 삭제 기능을 제공하는 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info/blog")
public class BlogEditController {

    private final BlogCommandService blogCommandService;

    @Operation(
            summary = "블로그 수정",
            description = "회원의 블로그 소개글을 업데이트합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.UPDATE_FAIL_BLOG
    })
    @PatchMapping("/intro")
    public String editBlogIntro(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid BlogUpdateIntroRequest request
    ) {
        blogCommandService.update(userDetails, request);
        return "블로그 소개글이 성공적으로 수정되었습니다.";
    }

    @Operation(
            summary = "블로그 임시 삭제 (비공개 처리)",
            description = "블로그를 비공개 상태로 설정합니다."
    )
    @ExceptionCodeAnnotations({CommonExceptionCode.UPDATE_FAIL_BLOG})
    @PatchMapping("/{blogId}/status")
    public String editBlogStatus(
            @Parameter(description = "블로그 ID", example = "1")
            @PathVariable @NotNull Long blogId
    ) {
        blogCommandService.updateStatus(blogId, false);
        return "블로그가 성공적으로 비활성화되었습니다.";
    }

}
