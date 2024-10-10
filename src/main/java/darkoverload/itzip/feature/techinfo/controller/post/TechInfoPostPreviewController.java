package darkoverload.itzip.feature.techinfo.controller.post;

import darkoverload.itzip.feature.techinfo.controller.post.response.PostPreviewResponse;
import darkoverload.itzip.feature.techinfo.service.post.PostPreviewService;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Tag(
        name = "Tech Info Posts Preview",
        description = "포스트 미리보기 기능을 제공하는 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info")
public class TechInfoPostPreviewController {

    private final PostPreviewService postPreviewService;

    @Operation(
            summary = "포스트 미리보기 조회",
            description = "카테고리 ID 및 정렬 기준에 따라 필터링된 포스트 미리보기를 조회한다."
    )
    @GetMapping("/posts/preview")
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
}