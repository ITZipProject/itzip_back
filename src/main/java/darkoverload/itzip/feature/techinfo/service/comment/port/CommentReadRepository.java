package darkoverload.itzip.feature.techinfo.service.comment.port;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentReadRepository {

    Page<Comment> findCommentsByPostId(Object id, Pageable pageable);

}
