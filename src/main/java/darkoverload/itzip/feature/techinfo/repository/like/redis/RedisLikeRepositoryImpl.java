package darkoverload.itzip.feature.techinfo.repository.like.redis;

import darkoverload.itzip.feature.techinfo.dto.like.LikeStatus;
import darkoverload.itzip.feature.techinfo.util.RedisKeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 를 사용하여 좋아요 상태를 관리하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class RedisLikeRepositoryImpl implements RedisLikeRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 사용자의 포스트 좋아요 상태를 Redis 에 저장합니다.
     *
     * @param userId  사용자 ID
     * @param postId  포스트 ID
     * @param isLiked 좋아요 상태
     * @param ttl     데이터 유효 시간(초)
     */
    @Override
    public void save(Long userId, String postId, boolean isLiked, long ttl) {
        String redisKey = RedisKeyUtil.buildRedisKey(userId, postId, "like");
        redisTemplate.opsForValue().set(redisKey, String.valueOf(isLiked), ttl, TimeUnit.SECONDS);
    }

    /**
     * 사용자의 특정 포스트에 대한 좋아요 상태를 조회합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 좋아요 상태 (Boolean), 없으면 null
     */
    @Override
    public Boolean getLikeStatus(Long userId, String postId) {
        String redisKey = RedisKeyUtil.buildRedisKey(userId, postId, "like");
        String isLiked = (String) redisTemplate.opsForValue().get(redisKey);
        return isLiked != null ? Boolean.valueOf(isLiked) : null;
    }

    /**
     * Redis 에 저장된 모든 좋아요 상태를 조회합니다.
     *
     * @return List<LikeStatus>
     */
    @Override
    public List<LikeStatus> getAllLikeStatuses() {
        List<LikeStatus> likeStatuses = new ArrayList<>();

        Set<String> keys = redisTemplate.keys("post:*:user:*:like");

        for (String key : keys) {
            String likeStatus = (String) redisTemplate.opsForValue().get(key);

            if (likeStatus != null) {
                Boolean isLiked = Boolean.valueOf(likeStatus);
                String[] parts = key.split(":");
                String postId = parts[1];
                Long userId = Long.valueOf(parts[3]);

                likeStatuses.add(LikeStatus.from(postId, userId, isLiked));
            }
        }

        return likeStatuses;
    }

    /**
     * Redis에 저장된 모든 좋아요 데이터를 삭제합니다.
     * 주로 테스트 환경이나 데이터 초기화에 사용됩니다.
     */
    @Override
    public void deleteAll() {
        // 좋아요 관련 모든 키 삭제
        redisTemplate.delete(redisTemplate.keys("post:*:user:*:like"));
    }

}
