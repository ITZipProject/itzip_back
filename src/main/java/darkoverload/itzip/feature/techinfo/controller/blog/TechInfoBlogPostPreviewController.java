package darkoverload.itzip.feature.techinfo.controller.blog;

import darkoverload.itzip.feature.techinfo.controller.post.response.PostBlogPreviewResponse;
import darkoverload.itzip.feature.techinfo.service.post.PostBlogPreviewService;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

@Tag(
        name = "Tech Info Blog Posts Preview",
        description = "블로그의 포스트 미리보기 기능을 제공하는 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info")
public class TechInfoBlogPostPreviewController {

    private final PostBlogPreviewService postBlogPreviewService;

    @Operation(
            summary = "블로그 포스트 미리보기 조회",
            description = "지정된 블로그 ID를 사용해 포스트의 미리보기 정보를 반환한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_BLOG)
    @GetMapping("/blog/{blogId}/posts/preview")
    public PagedModel<EntityModel<PostBlogPreviewResponse>> fetchBlogPostPreviews (
            @Parameter(description = "블로그 ID", example = "1") @PathVariable @NotNull Long blogId,
            @RequestParam(name = "sortType", required = false, defaultValue = "NEWEST") SortType sortType,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size)
    {
        return postBlogPreviewService.getPostsByBlogId(blogId, sortType, page, size);
    }
}