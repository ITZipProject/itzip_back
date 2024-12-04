package darkoverload.itzip.feature.techinfo.repository.like.redis;

import darkoverload.itzip.feature.techinfo.dto.like.LikeStatus;
import java.util.List;

public interface RedisLikeRepository {

    void save(Long userId, String postId, boolean isLiked, long ttl);

    Boolean getLikeStatus(Long userId, String postId);

    List<LikeStatus> getAllLikeStatuses();

}