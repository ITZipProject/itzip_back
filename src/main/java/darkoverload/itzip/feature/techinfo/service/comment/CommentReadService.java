package darkoverload.itzip.feature.techinfo.service.comment;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import darkoverload.itzip.feature.techinfo.domain.comment.CommentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentReadService {

    Page<Comment> findCommentsByPostId(String postId, Pageable pageable);

    Page<CommentDetails> getCommentsByPostId(String postId, int page, int size);

}
