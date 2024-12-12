package darkoverload.itzip.feature.techinfo.repository.like;

import darkoverload.itzip.feature.techinfo.dto.like.LikeStatus;
import darkoverload.itzip.feature.techinfo.repository.like.redis.RedisLikeRepository;
import darkoverload.itzip.feature.techinfo.service.like.port.LikeCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Redis 를 사용하여 좋아요 상태를 캐싱하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class LikeCacheRepositoryImpl implements LikeCacheRepository {

    private final RedisLikeRepository repository;

    /**
     * 사용자의 포스트 좋아요 상태를 캐시에 저장합니다.
     *
     * @param userId  사용자 ID
     * @param postId  포스트 ID
     * @param isLiked 좋아요 상태
     * @param ttl     캐시 유효 시간(초)
     */
    @Override
    public void save(Long userId, String postId, boolean isLiked, long ttl) {
        repository.save(userId, postId, isLiked, ttl);
    }

    /**
     * 사용자의 특정 포스트에 대한 좋아요 상태를 캐시에서 조회합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 좋아요 상태 (Boolean), 캐시에 없으면 null
     */
    @Override
    public Boolean getLikeStatus(Long userId, String postId) {
        return repository.getLikeStatus(userId, postId);
    }

    /**
     * 캐시에 저장된 모든 좋아요 상태를 조회합니다.
     *
     * @return List<LikeStatus>
     */
    @Override
    public List<LikeStatus> getAllLikeStatuses() {
        return repository.getAllLikeStatuses();
    }

    /**
     * 캐시에 저장된 모든 좋아요 데이터를 삭제합니다.
     * 주로 테스트 환경이나 캐시 초기화 용도로 사용됩니다.
     */
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}
