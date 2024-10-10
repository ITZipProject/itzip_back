package darkoverload.itzip.feature.techinfo.repository.scrap.cache;

import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatusDto;
import darkoverload.itzip.feature.techinfo.util.RedisKeyUtil;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScrapCacheRepositoryImpl implements ScrapCacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setScrapStatus(Long userId, String postId, boolean isScrappd, long ttl) {
        String redisKey = RedisKeyUtil.buildRedisKey(userId, postId, "scrap"); // Redis 키 생성
        redisTemplate.opsForValue().set(redisKey, String.valueOf(isScrappd), ttl, TimeUnit.SECONDS); // 스크랩 상태와 TTL 설정
    }

    @Override
    public Boolean getScrapStatus(Long userId, String postId) {
        String redisKey = RedisKeyUtil.buildRedisKey(userId, postId, "scrap"); //Redis 키 생성
        String isScrapped = (String) redisTemplate.opsForValue().get(redisKey); // Redis에서 스크랩 상태를 문자열로 조회

        return isScrapped != null ? Boolean.valueOf(isScrapped) : null;
    }

    @Override
    public List<ScrapStatusDto> getAllScrapStatuses() {
        List<ScrapStatusDto> scrapStatuses = new ArrayList<>(); // 스크랩 상태를 저장할 리스트 생성

        Set<String> keys = redisTemplate.keys("post:*:user:*:scrap"); // 모든 스크랩 키 조회

        for (String key : keys) {
            String scrapStatus = (String) redisTemplate.opsForValue().get(key); // Redis에서 스크랩 상태를 문자열로 조회

            if (scrapStatus != null) { // 스크랩 상태가 null이 아니면 처리
                Boolean isScrapped = Boolean.valueOf(scrapStatus); // 문자열을 Boolean으로 변환
                String[] parts = key.split(":"); // 키를 ":"로 분할하여 포스트ID와 유저ID 추출
                String postId = parts[1]; // 포스트ID 추출
                Long userId = Long.valueOf(parts[3]); // 유저ID 추출

                // ScrapStatusDto 객체 생성 후 리스트에 추가
                scrapStatuses.add(ScrapStatusDto.builder()
                        .userId(userId)
                        .postId(postId)
                        .isScrapped(isScrapped)
                        .build()
                );
            }
        }

        return scrapStatuses; // 스크랩 상태 리스트 반환
    }
}