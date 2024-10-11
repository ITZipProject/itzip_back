package darkoverload.itzip.feature.techinfo.controller.post;

import darkoverload.itzip.feature.techinfo.controller.post.response.PostBlogPreviewResponse;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostPreviewResponse;
import darkoverload.itzip.feature.techinfo.service.post.PostBlogPreviewService;
import darkoverload.itzip.feature.techinfo.service.post.PostPreviewService;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;

import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Tag(
        name = "Tech Info Post Preview",
        description = "포스트 및 블로그 포스트 미리보기 기능을 제공하는 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info/posts")
public class TechInfoPostPreviewController {

    private final PostPreviewService postPreviewService;
    private final PostBlogPreviewService postBlogPreviewService;

    @Operation(
            summary = "포스트 미리보기 조회",
            description = "카테고리 ID 및 정렬 기준에 따라 필터링된 포스트 미리보기를 조회한다."
    )
    @GetMapping("/preview")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_POSTS_FOR_CATEGORY,
            CommonExceptionCode.NOT_FOUND_POST
    })
    public PagedModel<EntityModel<PostPreviewResponse>> fetchFilteredPostPreviews (
            @Parameter(description = "카테고리 ID (선택사항)", example = "66ce18d84cb7d0b29ce602f5") @RequestParam(name = "categoryId", required = false) Optional<String> categoryId,
            @RequestParam(name = "sortType", required = false, defaultValue = "NEWEST") SortType sortType,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size)
    {
        return postPreviewService.getAllOrFilteredPosts(categoryId, sortType, page, size);
    }

    @Operation(
            summary = "블로그 포스트 미리보기 조회",
            description = "지정된 블로그 ID를 사용해 포스트의 미리보기 정보를 반환한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_BLOG)
    @GetMapping("/blog/{blogId}/preview")
    public PagedModel<EntityModel<PostBlogPreviewResponse>> fetchBlogPostPreviews (
            @Parameter(description = "블로그 ID", example = "1") @PathVariable @NotNull Long blogId,
            @RequestParam(name = "sortType", required = false, defaultValue = "NEWEST") SortType sortType,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size)
    {
        return postBlogPreviewService.getPostsByBlogId(blogId, sortType, page, size);
    }
}