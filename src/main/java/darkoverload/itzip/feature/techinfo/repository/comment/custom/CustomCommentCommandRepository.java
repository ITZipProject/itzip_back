package darkoverload.itzip.feature.techinfo.repository.comment.custom;

import org.bson.types.ObjectId;

public interface CustomCommentCommandRepository {

    long update(ObjectId commentId, Long userId, String content);

    long update(ObjectId commentId, Long userId, boolean status);

}
