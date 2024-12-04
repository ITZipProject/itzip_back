package darkoverload.itzip.feature.techinfo.repository.comment;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import darkoverload.itzip.feature.techinfo.service.comment.port.CommentCommandRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

/**
 * 댓글 명령(생성, 수정, 삭제)을 처리하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class CommentCommandRepositoryImpl implements CommentCommandRepository {

    private final MongoCommentCommandRepository repository;

    /**
     * 새로운 댓글을 저장합니다.
     *
     * @param comment 저장할 댓글
     */
    @Override
    public void save(Comment comment) {
        repository.save(CommentDocument.from(comment));
    }

    /**
     * 댓글 내용을 업데이트합니다.
     *
     * @param commentId 업데이트할 댓글의 ID
     * @param userId    댓글 작성자의 ID
     * @param content   새로운 댓글 내용
     * @throws RestApiException 댓글 업데이트 실패 시 발생
     */
    public void update(ObjectId commentId, Long userId, String content) {
        if (repository.update(commentId, userId, content) < 0) {
            throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_COMMENT);
        }
    }

    /**
     * 댓글의 공개 상태를 업데이트합니다.
     *
     * @param commentId 업데이트할 댓글의 ID
     * @param userId    댓글 작성자의 ID
     * @param status    새로운 공개 상태
     * @throws RestApiException 댓글 상태 업데이트 실패 시 발생
     */
    public void update(ObjectId commentId, Long userId, boolean status) {
        if (repository.update(commentId, userId, status) < 0) {
            throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_COMMENT);
        }
    }

}
