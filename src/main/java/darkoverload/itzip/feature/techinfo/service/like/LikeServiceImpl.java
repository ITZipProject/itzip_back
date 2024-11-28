package darkoverload.itzip.feature.techinfo.service.like;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.service.like.port.LikeCacheRepository;
import darkoverload.itzip.feature.techinfo.service.like.port.LikeRepository;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 좋아요 관련 서비스 구현 클래스.
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeCacheRepository likeCacheRepository;
    private final UserRepository userRepository;

    /**
     * 사용자의 좋아요 상태를 토글합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @param postId      포스트 ID
     * @return 토글 후 좋아요 상태
     * @throws RestApiException 사용자를 찾을 수 없을 때 발생
     */
    @Override
    @Transactional(readOnly = true)
    public boolean toggleLike(CustomUserDetails userDetails, String postId) {
        Long userId = userRepository.findByEmail(userDetails.getEmail())
                .map(UserEntity::convertToDomain)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                )
                .getId();

        boolean isLiked = isLiked(userId, postId);

        likeCacheRepository.save(userId, postId, !isLiked, 90);

        return !isLiked;
    }

    /**
     * 사용자의 특정 포스트에 대한 좋아요 상태를 확인합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 좋아요 상태
     */
    @Override
    public boolean isLiked(Long userId, String postId) {
        Boolean isLiked = likeCacheRepository.getLikeStatus(userId, postId);

        if (isLiked == null) {
            isLiked = likeRepository.existsByUserIdAndPostId(userId, new ObjectId(postId));
            likeCacheRepository.save(userId, postId, isLiked, 90);
        }

        return isLiked;
    }

}
