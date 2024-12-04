package darkoverload.itzip.feature.techinfo.service.like;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

public interface LikeService {

    boolean toggleLike(CustomUserDetails userDetails, String postId);

    boolean isLiked(Long userId, String postId);

}
