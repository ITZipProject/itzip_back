package darkoverload.itzip.feature.techinfo.domain.comment;

import darkoverload.itzip.feature.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

/**
 * 댓글의 상세 정보를 나타내는 도메인 클래스.
 * 댓글 ID, 작성자 프로필 이미지 URL, 닉네임, 내용, 작성 일시를 포함합니다.
 */
@Getter
public class CommentDetails {

    private final String commentId;
    private final String profileImagePath;
    private final String nickname;
    private final String content;
    private final LocalDateTime createDate;

    @Builder
    public CommentDetails(
            String commentId,
            String profileImagePath,
            String nickname,
            String content,
            LocalDateTime createDate
    ) {
        this.commentId = commentId;
        this.profileImagePath = profileImagePath;
        this.nickname = nickname;
        this.content = content;
        this.createDate = createDate;
    }

    /**
     * Comment 와 User 로부터 CommentDetails 생성합니다.
     *
     * @param comment 댓글 정보
     * @param user    댓글 작성자 정보
     * @return CommentDetails
     */
    public static CommentDetails from(Comment comment, User user) {
        return CommentDetails.builder()
                .commentId(comment.getId())
                .profileImagePath(user.getImageUrl())
                .nickname(user.getNickname())
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .build();
    }

}
