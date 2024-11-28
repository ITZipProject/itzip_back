package darkoverload.itzip.feature.techinfo.service.like.port;

import darkoverload.itzip.feature.techinfo.domain.like.Like;
import org.bson.types.ObjectId;

public interface LikeRepository {

    void save(Like like);

    boolean existsByUserIdAndPostId(Long userId, ObjectId postId);

    void deleteByUserIdAndPostId(Long userId, ObjectId postId);

}
