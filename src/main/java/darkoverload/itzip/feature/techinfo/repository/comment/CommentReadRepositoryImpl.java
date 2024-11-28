package darkoverload.itzip.feature.techinfo.repository.comment;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import darkoverload.itzip.feature.techinfo.service.comment.port.CommentReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * 댓글 조회를 처리하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class CommentReadRepositoryImpl implements CommentReadRepository {

    private final MongoCommentReadRepository repository;

    /**
     * 특정 포스트의 댓글을 페이징하여 조회합니다.
     *
     * @param id       포스트 ID
     * @param pageable 페이징 정보
     * @return Page<Comment>
     */
    public Page<Comment> findCommentsByPostId(Object id, Pageable pageable) {
        return repository.findCommentsByPostId(id, pageable).map(CommentDocument::toModel);
    }

}
