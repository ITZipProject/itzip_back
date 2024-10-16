package darkoverload.itzip.feature.techinfo.domain;

import darkoverload.itzip.feature.techinfo.controller.comment.request.CommentCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.comment.response.CommentResponse;
import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import darkoverload.itzip.feature.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * 댓글 정보를 나타내는 도메인 클래스.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    /**
     * 댓글의 고유 식별자.
     */
    private String id;

    /**
     * 댓글이 속한 포스트의 ID.
     */
    private String postId;

    /**
     * 댓글 작성자의 ID.
     */
    private Long userId;

    /**
     * 댓글 내용.
     */
    private String content;

    /**
     * 댓글의 공개 여부.
     */
    private Boolean isPublic;

    /**
     * 댓글 작성 일자.
     */
    private LocalDateTime createDate;

    /**
     * 댓글 생성 메서드.
     *
     * @param request 댓글 생성 요청 객체
     * @param userId 댓글 작성자의 ID
     * @return 생성된 Comment 객체
     */
    public static Comment createComment(CommentCreateRequest request, Long userId) {
        return Comment.builder()
                .postId(request.getPostId())
                .userId(userId)
                .content(request.getContent())
                .isPublic(true)
                .build();
    }

    /**
     * Comment 객체를 CommentDocument로 변환 (댓글 ID 제외).
     *
     * @return 변환된 CommentDocument 객체
     */
    public CommentDocument convertToDocumentWithoutCommentId() {
        return CommentDocument.builder()
                .postId(new ObjectId(this.postId))
                .userId(this.userId)
                .content(this.content)
                .isPublic(this.isPublic)
                .build();
    }

    /**
     * Comment 객체를 CommentResponse로 변환.
     *
     * @param user 댓글 작성자 정보
     * @return 변환된 CommentResponse 객체
     */
    public CommentResponse convertToCommentResponse(User user) {
        return CommentResponse.builder()
                .commentId(this.id)
                .profileImagePath(user.getImageUrl())
                .nickname(user.getNickname())
                .content(this.content)
                .createDate(this.createDate.toString())
                .build();
    }
}