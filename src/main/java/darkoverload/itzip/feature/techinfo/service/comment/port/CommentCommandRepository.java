package darkoverload.itzip.feature.techinfo.service.comment.port;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import org.bson.types.ObjectId;

import java.util.List;

public interface CommentCommandRepository {

    Comment save(Comment comment);

    List<Comment> saveAll(List<Comment> comments);

    Comment update(ObjectId commentId, Long userId, String content);

    Comment update(ObjectId commentId, Long userId, boolean status);

    void deleteAll();

}
