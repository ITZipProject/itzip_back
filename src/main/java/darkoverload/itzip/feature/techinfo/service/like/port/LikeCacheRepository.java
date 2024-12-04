package darkoverload.itzip.feature.techinfo.service.like.port;

import darkoverload.itzip.feature.techinfo.dto.like.LikeStatus;
import java.util.List;

public interface LikeCacheRepository {

    void save(Long userId, String postId, boolean isLiked, long ttl);

    Boolean getLikeStatus(Long userId, String postId);

    List<LikeStatus> getAllLikeStatuses();

}
