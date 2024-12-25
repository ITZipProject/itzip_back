package darkoverload.itzip.feature.techinfo.controller.post.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        description = "기술 정보 게시글 댓글 수정 요청"
)
public record PostCommentUpdateRequest(
        @Schema(description = "댓글 ID", example = "674843181801af00dd3cbfee", required = true)
        @NotBlank(message = "댓글 ID는 필수입니다.")
        String commentId,

        @Schema(description = "수정할 댓글 내용", example = "수정된 댓글 내용입니다.", required = true)
        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content
) {
}
