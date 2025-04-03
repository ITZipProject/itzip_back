package darkoverload.itzip.feature.techinfo.ui.payload.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "댓글 등록 요청에 대한 Payload")
public record CommentRegistrationRequest(
        @Schema(
                description = "아티클 ID",
                example = "66e724e50000000000db4e53"
        )
        @NotBlank(message = "아티클 ID는 필수입니다.")
        @JsonProperty("article_id")
        String articleId,
        @Schema(
                description = "댓글 내용",
                example = "댓글 내용입니다."
        )
        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content
) { }
