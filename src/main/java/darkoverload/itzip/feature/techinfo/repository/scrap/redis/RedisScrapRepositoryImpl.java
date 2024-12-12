package darkoverload.itzip.feature.techinfo.repository.scrap.redis;

import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatus;
import darkoverload.itzip.feature.techinfo.util.RedisKeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 를 사용하여 스크랩 상태를 관리하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class RedisScrapRepositoryImpl implements RedisScrapRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 사용자의 포스트 스크랩 상태를 Redis 에 저장합니다.
     *
     * @param userId     사용자 ID
     * @param postId     포스트 ID
     * @param isScrapped 스크랩 상태
     * @param ttl        데이터 유효 시간(초)
     */
    @Override
    public void save(Long userId, String postId, boolean isScrapped, long ttl) {
        String redisKey = RedisKeyUtil.buildRedisKey(userId, postId, "scrap");
        redisTemplate.opsForValue().set(redisKey, String.valueOf(isScrapped), ttl, TimeUnit.SECONDS);
    }

    /**
     * 사용자의 특정 포스트에 대한 스크랩 상태를 조회합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 스크랩 상태 (Boolean), 없으면 null
     */
    @Override
    public Boolean getScrapStatus(Long userId, String postId) {
        String redisKey = RedisKeyUtil.buildRedisKey(userId, postId, "scrap");
        String isScrapped = (String) redisTemplate.opsForValue().get(redisKey);
        return isScrapped != null ? Boolean.valueOf(isScrapped) : null;
    }

    /**
     * Redis에 저장된 모든 스크랩 상태를 조회합니다.
     *
     * @return List<ScrapStatus>
     */
    @Override
    public List<ScrapStatus> getAllScrapStatuses() {
        List<ScrapStatus> scrapStatuses = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("post:*:user:*:scrap");

        for (String key : keys) {
            String scrapStatus = (String) redisTemplate.opsForValue().get(key);
            if (scrapStatus != null) {
                Boolean isScrapped = Boolean.valueOf(scrapStatus);
                String[] parts = key.split(":");
                String postId = parts[1];
                Long userId = Long.valueOf(parts[3]);
                scrapStatuses.add(ScrapStatus.from(postId, userId, isScrapped));
            }
        }

        return scrapStatuses;
    }

    /**
     * Redis에 저장된 모든 스크랩 데이터를 삭제합니다.
     * 주로 테스트 환경이나 데이터 초기화에 사용됩니다.
     */
    @Override
    public void deleteAll() {
        redisTemplate.delete(redisTemplate.keys("post:*:user:*:scrap"));
    }

}
