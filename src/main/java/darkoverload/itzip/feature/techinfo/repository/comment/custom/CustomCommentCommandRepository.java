package darkoverload.itzip.feature.techinfo.repository.comment.custom;

import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import org.bson.types.ObjectId;
import java.util.Optional;

public interface CustomCommentCommandRepository {

    Optional<CommentDocument> update(ObjectId commentId, Long userId, String content);

    Optional<CommentDocument> update(ObjectId commentId, Long userId, boolean status);

}
