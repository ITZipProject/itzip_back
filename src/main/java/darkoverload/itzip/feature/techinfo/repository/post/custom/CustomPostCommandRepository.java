package darkoverload.itzip.feature.techinfo.repository.post.custom;

import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import org.bson.types.ObjectId;
import java.util.Optional;
import java.util.List;

public interface CustomPostCommandRepository {

    Optional<PostDocument> update(ObjectId postId, ObjectId categoryId, String title, String content, String thumbnailImagePath,
                                 List<String> contentImagePaths);

    Optional<PostDocument> update(ObjectId postId, boolean status);

    Optional<PostDocument> updateFieldWithValue(ObjectId postId, String fieldName, int value);

}
