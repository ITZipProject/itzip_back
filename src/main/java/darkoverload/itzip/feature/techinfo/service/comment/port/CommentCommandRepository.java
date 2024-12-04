package darkoverload.itzip.feature.techinfo.service.comment.port;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import org.bson.types.ObjectId;

public interface CommentCommandRepository {

    void save(Comment comment);

    void update(ObjectId commentId, Long userId, String content);

    void update(ObjectId commentId, Long userId, boolean status);

}
