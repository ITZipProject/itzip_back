package darkoverload.itzip.feature.techinfo.controller.comment.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(
        description = "댓글의 상세 정보를 반환하는 응답 객체로, 댓글 ID, 작성자 정보, 본문 내용 및 작성 날짜를 포함합니다."
)
@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {

    @Schema(description = "댓글의 고유 ID", example = "66eaeacb48e1841cc9893a60")
    private String commentId;

    @Schema(description = "댓글 작성자의 프로필 이미지 경로", example = "")
    private String profileImagePath;

    @Schema(description = "댓글 작성자의 닉네임", example = "hyoseung")
    private String nickname;

    @Schema(description = "댓글 본문 내용", example = "이 포스트 정말 유익하네요!")
    private String content;

    @Schema(description = "댓글 작성 일자", example = "2024-09-18T23:59:23.242")
    private String createDate;
}