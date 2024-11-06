package darkoverload.itzip.feature.techinfo.repository.comment.custom;

import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CommentDocument와 관련된 커스텀 데이터베이스 작업을 정의하는 인터페이스.
 * 포스트 ID로 댓글 조회, 댓글 내용 업데이트, 댓글 공개 여부 변경 등의 메서드를 포함함.
 */
public interface CustomCommentRepository {

    /**
     * 주어진 댓글 ID에 해당하는 댓글 내용을 업데이트.
     *
     * @param commentId 댓글 ID
     * @param content 새로운 댓글 내용
     * @return 업데이트 성공 여부
     */
    boolean updateComment(ObjectId commentId, Long userId, String content);

    /**
     * 주어진 댓글 ID와 사용자 ID에 해당하는 댓글의 공개 여부를 변경합니다.
     *
     * @param commentId 댓글 ID
     * @param userId 사용자 ID
     * @param isPublic 공개 여부 (true: 공개, false: 비공개)
     * @return 업데이트 성공 여부
     */
    boolean updateCommentVisibility(ObjectId commentId, Long userId, boolean isPublic);

    /**
     * 주어진 포스트 ID에 해당하는 댓글 목록을 페이지네이션하여 조회.
     *
     * @param postId 포스트 ID
     * @param pageable 페이지네이션 정보
     * @return 페이지네이션된 댓글 목록
     */
    Page<CommentDocument> findCommentsByPostId(ObjectId postId, Pageable pageable);
}