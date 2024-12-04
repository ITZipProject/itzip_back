package darkoverload.itzip.feature.techinfo.controller.post.response;

import darkoverload.itzip.feature.techinfo.domain.comment.CommentDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(
        description = "기술 정보 게시글 댓글 상세 응답"
)
@Builder
public record PostCommentResponse(
        @Schema(description = "댓글 ID", example = "66eaeacb48e1841cc9893a60")
        String commentId,

        @Schema(description = "작성자 프로필 이미지 URL", example = "https://dy1vg9emkijkn.cloudfront.net/profile/19cc111f-c8f4-4d64-bd7a-129415e3ffa2.jpg")
        String profileImagePath,

        @Schema(description = "작성자 닉네임", example = "hyoseung")
        String nickname,

        @Schema(description = "댓글 내용", example = "이 포스트 정말 유익하네요!")
        String content,

        @Schema(description = "작성일", example = "2024-09-18T23:59:23.242")
        String createDate
) {

    public static PostCommentResponse from(CommentDetails commentDetails) {
        return new PostCommentResponse(
                commentDetails.getCommentId(),
                commentDetails.getProfileImagePath(),
                commentDetails.getNickname(),
                commentDetails.getContent(),
                commentDetails.getCreateDate().toString()
        );
    }

}