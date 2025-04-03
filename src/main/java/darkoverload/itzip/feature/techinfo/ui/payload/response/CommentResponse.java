package darkoverload.itzip.feature.techinfo.ui.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkoverload.itzip.feature.techinfo.domain.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "댓글 응답 정보")
@Builder
public record CommentResponse(
        @Schema(description = "프로필 이미지 URI", example = "http://example.com/profile.jpg")
        @JsonProperty("profile_image_uri")
        String profileImageUrI,

        @Schema(description = "사용자 닉네임", example = "JohnDoe")
        String nickname,

        @Schema(description = "댓글 ID", example = "12345")
        @JsonProperty("comment_id")
        long commentId,

        @Schema(description = "댓글 내용", example = "이것은 댓글 내용입니다.")
        String content,

        @Schema(description = "작성 시간", example = "2025-03-13T12:34:56")
        @JsonProperty("create_at")
        String createAt
) {

    public static CommentResponse from(final Comment comment) {
        return CommentResponse.builder()
                .profileImageUrI(comment.getUser().getImageUrl())
                .nickname(comment.getUser().getNickname())
                .commentId(comment.getId())
                .content(comment.getContent())
                .createAt(comment.getCreatedAt().toString())
                .build();
    }

}
