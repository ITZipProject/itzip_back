package darkoverload.itzip.feature.techinfo.service.post.port;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import org.bson.types.ObjectId;
import java.util.List;

public interface PostCommandRepository {

    Post save(Post post);

    List<Post> saveAll(List<Post> posts);

    Post update(ObjectId postId, ObjectId categoryId, String title, String content, String thumbnailImagePath, List<String> contentImagePaths);

    Post update(ObjectId postId, boolean status);

    Post updateFieldWithValue(ObjectId postId, String fieldName, int value);

    void deleteAll();

}
