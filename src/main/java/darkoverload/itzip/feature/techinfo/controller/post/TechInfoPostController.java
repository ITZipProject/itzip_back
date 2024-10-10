package darkoverload.itzip.feature.techinfo.controller.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogAdjacentPostsResponse;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostDetailInfoResponse;
import darkoverload.itzip.feature.techinfo.service.post.PostService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.Valid;

import java.time.LocalDateTime;

@Tag(
        name = "Tech Info Post",
        description = "포스트 생성, 수정, 조회 및 삭제 기능을 제공하는 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info")
public class TechInfoPostController {

    private final PostService postService;

    @Operation(
            summary = "인접한 포스트 조회",
            description = "주어진 블로그 ID와 생성 날짜를 사용해 인접한 포스트을 조회한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_BLOG)
    @GetMapping("/posts/adjacent")
    public BlogAdjacentPostsResponse fetchAdjacentPosts(
            @Parameter(description = "블로그 ID", example = "1") @RequestParam(value = "blogId") @NotNull Long blogId,
            @Parameter(description = "생성 날짜", example = "2024-09-16T03:18:13.734") @RequestParam("createDate") @NotNull LocalDateTime createDate)
    {
        return postService.getAdjacentPosts(blogId, createDate);
    }

    /**
     * 특정 포스트 ID로 조회, isPublic 필드를 제외하고 가져옴
     *
     * @param postId 포스트의 ObjectId
     * @return {@link PostDetailInfoResponse} isPublic 필드를 제외한 포스트 정보
     */
    @Operation(
            summary = "포스트 상세 조회",
            description = "특정 포스트 ID로 포스트의 상세 정보를 조회한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.NOT_FOUND_POST,
            CommonExceptionCode.NOT_FOUND_BLOG
    })
    @GetMapping("/post/{postId}")
    public PostDetailInfoResponse retrievePostDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "포스트 ID", example = "66e724e50000000000db4e53") @PathVariable @NotBlank String postId)
    {
        return postService.getPostDetailById(userDetails, postId);
    }

    @Operation(
            summary = "포스트 생성",
            description = "새로운 포스트을 생성한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.NOT_FOUND_BLOG
    })
    @PostMapping("/post")
    public String createPost(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid PostCreateRequest request)
    {
        postService.addNewPost(userDetails, request);
        return "포스트을 생성했습니다.";
    }

    @Operation(
            summary = "포스트 수정",
            description = "주어진 요청을 기반으로 포스트을 수정한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_POST)
    @PatchMapping("/post")
    public String editPostDetails(
            @RequestBody @Valid PostUpdateRequest request)
    {
        postService.modifyPost(request);
        return "포스트을 수정했습니다.";
    }

    @Operation(
            summary = "포스트 삭제",
            description = "특정 포스트을 비활성화하여 삭제한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_POST)
    @PatchMapping("/post/hide/{postId}")
    public String deletePost(
            @Parameter(description = "포스트 ID", example = "66e724e50000000000db4e53") @PathVariable @NotBlank String postId)
    {
        postService.hidePost(postId);
        return "포스트을 삭제했습니다.";
    }
}