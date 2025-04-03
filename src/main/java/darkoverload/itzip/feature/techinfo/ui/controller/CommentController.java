package darkoverload.itzip.feature.techinfo.ui.controller;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.generator.PagedModelGenerator;
import darkoverload.itzip.feature.techinfo.ui.payload.response.CommentResponse;
import darkoverload.itzip.feature.techinfo.application.service.command.CommentCommandService;
import darkoverload.itzip.feature.techinfo.application.service.query.CommentQueryService;
import darkoverload.itzip.feature.techinfo.ui.payload.request.comment.CommentEditRequest;
import darkoverload.itzip.feature.techinfo.ui.payload.request.comment.CommentRegistrationRequest;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import darkoverload.itzip.global.config.swagger.SwaggerRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Tech Info Comment",
        description = "댓글 조회, 등록, 변경, 취소을 제공하는 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info")
public class CommentController {

    private final CommentCommandService commandService;
    private final CommentQueryService queryService;

    @Operation(
            summary = "아티클 댓글 목록 조회",
            description = "지정된 아티클 ID에 해당하는 댓글 목록을 반환합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.UNAUTHORIZED,
            CommonExceptionCode.COMMENT_NOT_FOUND
    })
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @GetMapping("/{article_id}/comments")
    public ResponseEntity<PagedModel<EntityModel<CommentResponse>>> getCommentsByArticleId(
            @Parameter(
                    description = "댓글을 조회할 아티클의 고유 식별자",
                    required = true,
                    example = "67d2b940d88d2b9236a1faff"
            )
            @NotBlank(message = "아티클 ID는 필수 입니다.")
            @PathVariable(name = "article_id") final String articleId,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size
    ) {
        final Page<CommentResponse> response = queryService.getCommentsByArticleId(articleId, page, size);
        final PagedModel<EntityModel<CommentResponse>> pagedModel = PagedModelGenerator.generate(response);
        return ResponseEntity.ok(pagedModel);
    }

    @Operation(
            summary = "댓글 등록",
            description = "특정 아티클에 새 댓글을 등록합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.UNAUTHORIZED,
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.ARTICLE_NOT_FOUND
    })
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @PostMapping("/comment")
    public ResponseEntity<String> registerComment(
            @Parameter(hidden = true) @AuthenticationPrincipal final CustomUserDetails userDetails,
            @SwaggerRequestBody(
                    description = "등록할 댓글 정보를 담은 요청 Payload",
                    content = @Content(schema = @Schema(implementation = CommentRegistrationRequest.class))
            )
            @RequestBody final CommentRegistrationRequest request
    ) {
        commandService.create(userDetails, request);
        return ResponseEntity.ok("댓글이 성공적으로 등록되었습니다.");
    }

    @Operation(
            summary = "댓글 변경",
            description = "댓글을 변경합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.UNAUTHORIZED,
            CommonExceptionCode.ARTICLE_NOT_FOUND
    })
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @PatchMapping("/comment")
    public ResponseEntity<String> modify(
            @Parameter(hidden = true) @AuthenticationPrincipal final CustomUserDetails userDetails,
            @SwaggerRequestBody(
                    description = "변경할 댓글 정보를 담은 요청 Payload",
                    content = @Content(schema = @Schema(implementation = CommentEditRequest.class))
            )
            @RequestBody final CommentEditRequest request
    ) {
        commandService.update(userDetails, request);
        return ResponseEntity.ok("댓글이 성공적으로 변경되었습니다.");
    }

    @Operation(
            summary = "댓글 등록 취소",
            description = "댓글을 비공개 상태로 전환합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.UNAUTHORIZED,
            CommonExceptionCode.ARTICLE_NOT_FOUND
    })
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> cancel(
            @Parameter(hidden = true) @AuthenticationPrincipal final CustomUserDetails userDetails,
            @Parameter(
                    description = "취소할 댓글의 고유 식별자",
                    required = true,
                    example = "1"
            )
            @NotBlank(message = "댓글 ID는 필수 입니다.")
            @PathVariable(name = "id") final Long commentId
    ) {
        commandService.delete(userDetails, commentId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }

}
