package darkoverload.itzip.feature.techinfo.domain.comment;

import darkoverload.itzip.feature.techinfo.controller.post.request.PostCommentCreateRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 기술 정보 게시글의 댓글을 나타내는 도메인 클래스.
 * 댓글 ID, 게시글 ID, 작성자 ID, 내용, 공개 여부, 작성 일시를 포함합니다.
 */
@Getter
public class Comment {

    private final String id;
    private final String postId;
    private final Long userId;
    private final String content;
    private final Boolean isPublic;
    private final LocalDateTime createDate;

    @Builder
    public Comment(String id, String postId, Long userId, String content, Boolean isPublic, LocalDateTime createDate) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.isPublic = isPublic;
        this.createDate = createDate;
    }

    /**
     * 댓글 생성 요청과 사용자 ID로 새 Comment 생성합니다.
     *
     * @param request 댓글 생성 요청
     * @param userId  댓글 작성자의 ID
     * @return Comment
     */
    public static Comment from(PostCommentCreateRequest request, Long userId) {
        return Comment.builder()
                .postId(request.postId())
                .userId(userId)
                .content(request.content())
                .isPublic(true)
                .build();
    }

}
