package darkoverload.itzip.feature.techinfo.repository.post.custom;

import org.bson.types.ObjectId;

import java.util.List;

public interface CustomPostCommandRepository {

    long update(ObjectId postId, ObjectId categoryId, String title, String content, String thumbnailImagePath,
                List<String> contentImagePaths);

    long update(ObjectId postId, boolean status);

    long updateFieldWithValue(ObjectId postId, String fieldName, int value);

}
