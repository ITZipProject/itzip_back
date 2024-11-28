package darkoverload.itzip.feature.techinfo.repository.comment.custom;

import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCommentReadRepository {

    Page<CommentDocument> findCommentsByPostId(Object id, Pageable pageable);

}
