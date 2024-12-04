package darkoverload.itzip.feature.techinfo.service.post.port;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import org.bson.types.ObjectId;

import java.util.List;

public interface PostCommandRepository {

    void save(Post post);

    void update(ObjectId postId, ObjectId categoryId, String title, String content, String thumbnailImagePath,
                List<String> contentImagePaths);

    void update(ObjectId postId, boolean status);

    void updateFieldWithValue(ObjectId postId, String fieldName, int value);

}
