package darkoverload.itzip.feature.techinfo.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(
        description = "댓글 수정을 위한 요청 객체로, 수정할 댓글의 ID와 변경된 내용을 포함합니다."
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequest {

    @Schema(description = "댓글 ID", example = "66eaeacb48e1841cc9893a60", required = true)
    @NotBlank(message = "댓글 ID는 필수 입력 항목입니다.")
    private String commentId;

    @Schema(description = "수정할 댓글 내용", example = "수정된 댓글 내용입니다.", required = true)
    @NotBlank(message = "댓글은 필수 입력 항목입니다.")
    private String content;
}