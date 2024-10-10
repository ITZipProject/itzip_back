package darkoverload.itzip.feature.techinfo.controller.comment;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.comment.request.CommentCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.comment.request.CommentUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.comment.response.CommentResponse;
import darkoverload.itzip.feature.techinfo.service.comment.CommentService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(
        name = "Tech Info Comment",
        description = "댓글 생성, 조회, 수정 및 삭제 기능을 제공하는 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info")
public class TechInfoCommentController {

    private final CommentService commentService;

    @Operation(
            summary = "댓글 목록 조회",
            description = "특정 포스트의 댓글을 필터링하여 페이지네이션된 결과로 반환한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_USER)
    @GetMapping("/comments")
    public PagedModel<EntityModel<CommentResponse>> fetchFilteredComments(
            @Parameter(description = "포스트 ID", example = "66e724e50000000000db4e53") @RequestParam(name = "postId") String postId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size)
    {
        return commentService.getFilteredComments(postId, page, size);
    }

    @Operation(
            summary = "댓글 생성",
            description = "새로운 댓글을 생성한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.NOT_FOUND_POST
    })
    @PostMapping("/comment")
    public String createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CommentCreateRequest request)
    {
        commentService.addNewComment(userDetails, request);
        return "댓글을 생성했습니다.";
    }

    @Operation(
            summary = "댓글 수정",
            description = "특정 댓글의 내용을 수정한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.NOT_FOUND_COMMENT
    })
    @PatchMapping("/comment")
    public String editComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CommentUpdateRequest request)
    {
        commentService.modifyComment(userDetails, request);
        return "댓글을 수정했습니다.";
    }

    @Operation(
            summary = "댓글 삭제",
            description = "특정 댓글을 비활성화하여 삭제한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.NOT_FOUND_COMMENT,
    })
    @PatchMapping("/comment/{commentId}/hide")
    public String deleteComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "댓글 ID", example = "66eaeacb48e1841cc9893a60") @PathVariable String commentId)
    {
        commentService.hideComment(userDetails, commentId);
        return "댓글을 삭제했습니다.";
    }
}