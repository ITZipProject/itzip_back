package darkoverload.itzip.feature.techinfo.controller.comment.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(
        description = "댓글 생성을 위한 요청 객체로, 포스트 ID, 작성자의 닉네임, 그리고 댓글 내용을 포함합니다."
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {

    @Schema(description = "포스트 ID", example = "66e724e50000000000db4e53", required = true)
    @NotBlank(message = "포스트 ID는 필수 입력 항목입니다.")
    private String postId;

    @Schema(description = "댓글 내용", example = "이 포스트 정말 유익하네요!", required = true)
    @NotBlank(message = "댓글 내용은 필수 입력 항목입니다.")
    private String content;
}