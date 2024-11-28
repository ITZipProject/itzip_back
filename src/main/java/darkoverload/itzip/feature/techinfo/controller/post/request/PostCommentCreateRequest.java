package darkoverload.itzip.feature.techinfo.controller.post.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "기술 정보 게시글 댓글 작성 요청")
public record PostCommentCreateRequest(
        @Schema(description = "게시글 ID", example = "66e724e50000000000db4e53")
        @NotBlank(message = "게시글 ID는 필수입니다.")
        String postId,

        @Schema(description = "댓글 내용", example = "이 포스트 정말 유익하네요!")
        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content
) {
}
