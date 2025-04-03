package darkoverload.itzip.feature.techinfo.ui.payload.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "댓글 변경 요청에 대한 Payload")
public record CommentEditRequest(
        @Schema(
                description = "댓글 ID",
                example = "999"
        )
        @NotNull(message = "댓글 ID는 필수입니다.")
        @JsonProperty("comment_id")
        Long commentId,

        @Schema(
                description = "댓글 내용",
                example = "수정된 댓글 내용"
        )
        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content
) { }
