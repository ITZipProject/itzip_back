package darkoverload.itzip.feature.techinfo.controller.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostDetailsInfoResponse;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostPreviewResponse;
import darkoverload.itzip.feature.techinfo.service.post.PostCommandService;
import darkoverload.itzip.feature.techinfo.service.post.PostReadService;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.feature.techinfo.util.PagedModelUtil;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Tech Info Post",
        description = "게시글 조회, 생성, 수정 및 삭제 기능을 제공하는 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info")
public class PostController {

    private final PostReadService postReadService;
    private final PostCommandService postCommandService;

    @Operation(
            summary = "전체 기술 정보 게시글 수 조회",
            description = "현재 시스템에 등록된 모든 기술 정보 포스트의 총 개수를 반환합니다."
    )
    @GetMapping("/post/count")
    public long getPostCount() {
        return postReadService.getPostCount();
    }

    @Operation(
            summary = "게시글 상세 조회",
            description = "특정 게시글의 상세 정보를 조회합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.NOT_FOUND_POST,
            CommonExceptionCode.NOT_FOUND_BLOG
    })
    @GetMapping("/post/{postId}")
    public PostDetailsInfoResponse viewPostDetails(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "게시글 ID", example = "66e724e50000000000db4e53")
            @PathVariable @NotBlank String postId
    ) {
        return PostDetailsInfoResponse.from(postReadService.getPostDetailsById(postId, userDetails));
    }

    @Operation(
            summary = "게시글 미리보기 목록 조회",
            description = "카테고리별 필터링 및 정렬된 게시글 미리보기 목록을 페이징하여 반환합니다."
    )
    @GetMapping("/posts/preview")
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_POST_IN_CATEGORY,
            CommonExceptionCode.NOT_FOUND_POST,
            CommonExceptionCode.NOT_FOUND_BLOG
    })
    public PagedModel<EntityModel<PostPreviewResponse>> getPostPreviews(
            @Parameter(description = "카테고리 ID (선택사항)", example = "66ce18d84cb7d0b29ce602f5")
            @RequestParam(name = "categoryId", required = false) String categoryId,
            @RequestParam(name = "sortType", required = false, defaultValue = "NEWEST") SortType sortType,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size
    ) {
        Page<PostPreviewResponse> postPreviewResponses = postReadService.getAllOrPostsByCategoryId(categoryId, page,
                        size, sortType)
                .map(PostPreviewResponse::from);

        return PagedModelUtil.create(postPreviewResponses);
    }

    @Operation(
            summary = "게시글 작성",
            description = "새로운 기술 정보 게시글을 작성합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.NOT_FOUND_BLOG
    })
    @PostMapping("/post")
    public String addPost(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid PostCreateRequest request
    ) {
        postCommandService.create(userDetails, request);
        return "게시글이 성공적으로 작성되었습니다.";
    }

    @Operation(
            summary = "게시글 수정",
            description = "주어진 요청을 기반으로 포스트를 수정합니다."
    )
    @ExceptionCodeAnnotations({CommonExceptionCode.UPDATE_FAIL_POST})
    @PatchMapping("/post")
    public String editPost(@RequestBody @Valid PostUpdateRequest request) {
        postCommandService.update(request);
        return "게시글이 성공적으로 수정되었습니다.";
    }

    @Operation(
            summary = "게시글 임시 삭제 (비공개 처리)",
            description = "게시글을 비공개 상태로 설정합니다."
    )
    @ExceptionCodeAnnotations({CommonExceptionCode.UPDATE_FAIL_POST})
    @PatchMapping("/post/{postId}/unpublish")
    public String unpublishPost(
            @Parameter(
                    description = "게시글 ID",
                    example = "66e724e50000000000db4e53"
            )
            @PathVariable @NotBlank String postId
    ) {
        postCommandService.update(postId, false);
        return "게시글이 성공적으로 비공개 처리되었습니다.";
    }

}
