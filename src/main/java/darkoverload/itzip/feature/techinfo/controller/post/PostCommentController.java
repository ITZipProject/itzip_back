package darkoverload.itzip.feature.techinfo.controller.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCommentCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCommentUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostCommentResponse;
import darkoverload.itzip.feature.techinfo.service.comment.CommentCommandService;
import darkoverload.itzip.feature.techinfo.service.comment.CommentReadService;
import darkoverload.itzip.feature.techinfo.util.PagedModelUtil;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Tech Info Post Comment",
        description = "댓글 조회, 생성, 수정 및 삭제 기능을 제공하는 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info/post")
public class PostCommentController {

    private final CommentReadService commentReadService;
    private final CommentCommandService commentCommandService;

    @Operation(
            summary = "게시글 댓글 목록 조회",
            description = "특정 게시글에 대한 댓글 목록을 페이징 처리하여 반환합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.NOT_FOUND_POST
    })
    @GetMapping("/comments")
    public PagedModel<EntityModel<PostCommentResponse>> getPostComments(
            @Parameter(description = "게시글 ID", example = "66e724e50000000000db4e53") @RequestParam(name = "postId") @NotBlank String postId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<PostCommentResponse> postCommentResponsePage = commentReadService.getCommentsByPostId(postId, page, size)
                .map(PostCommentResponse::from);

        return PagedModelUtil.create(postCommentResponsePage);
    }

    @Operation(
            summary = "게시글에 댓글 작성",
            description = "특정 게시글에 새 댓글을 작성합니다."
    )
    @ExceptionCodeAnnotations({CommonExceptionCode.NOT_FOUND_USER})
    @PostMapping("/comment")
    public String addComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PostCommentCreateRequest request
    ) {
        commentCommandService.create(userDetails, request);
        return "댓글이 성공적으로 작성되었습니다.";
    }

    @Operation(
            summary = "게시글에 댓글 수정",
            description = "특정 댓글의 내용을 수정합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.UPDATE_FAIL_COMMENT
    })
    @PatchMapping("/comment")
    public String editComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PostCommentUpdateRequest request
    ) {
        commentCommandService.update(userDetails, request);
        return "게시글이 성공적으로 수정되었습니다.";
    }

    @Operation(
            summary = "게시글에 댓글 임시 삭제 (비공개 처리)",
            description = "게시글에 댓글을 비공개 상태로 설정합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.UPDATE_FAIL_COMMENT
    })
    @PatchMapping("/{commentId}/unpublish")
    public String unpublishComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "댓글 ID", example = "66eaeacb48e1841cc9893a60") @PathVariable String commentId
    ) {
        commentCommandService.updateVisibility(userDetails, commentId, false);
        return "댓글이 성공적으로 비공개 처리되었습니다.";
    }

}
