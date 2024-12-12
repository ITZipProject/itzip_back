package darkoverload.itzip.feature.techinfo.repository.comment;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import darkoverload.itzip.feature.techinfo.service.comment.port.CommentCommandRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Comment save(Comment comment) {
        return repository.save(CommentDocument.from(comment)).toModel();
    }

    /**
     * 여러 댓글을 한꺼번에 저장합니다.
     *
     * @param comments 저장할 댓글 리스트
     * @return 저장된 댓글 리스트
     */
    @Override
    public List<Comment> saveAll(List<Comment> comments) {
        return repository.saveAll(
                comments.stream()
                        .map(CommentDocument::from)
                        .toList()
        ).stream()
                .map(CommentDocument::toModel)
                .toList();
    }

    /**
     * 댓글 내용을 업데이트합니다.
     *
     * @param commentId 업데이트할 댓글의 ID
     * @param userId    댓글 작성자의 ID
     * @param content   새로운 댓글 내용
     * @throws RestApiException 댓글 업데이트 실패 시 발생
     */
    @Override
    public Comment update(ObjectId commentId, Long userId, String content) {
        return repository.update(commentId, userId, content)
                .map(CommentDocument::toModel)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.UPDATE_FAIL_COMMENT)
                );
    }

    /**
     * 댓글의 공개 상태를 업데이트합니다.
     *
     * @param commentId 업데이트할 댓글의 ID
     * @param userId    댓글 작성자의 ID
     * @param status    새로운 공개 상태
     * @throws RestApiException 댓글 상태 업데이트 실패 시 발생
     */
    @Override
    public Comment update(ObjectId commentId, Long userId, boolean status) {
        return repository.update(commentId, userId, status)
                .map(CommentDocument::toModel)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.UPDATE_FAIL_COMMENT)
                );
    }

    /**
     * 모든 댓글을 삭제합니다.
     * 주로 테스트 환경이나 데이터 초기화에 사용됩니다.
     */
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}
