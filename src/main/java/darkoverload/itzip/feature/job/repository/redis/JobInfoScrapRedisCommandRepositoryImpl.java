package darkoverload.itzip.feature.job.repository.redis;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrapType;
import darkoverload.itzip.feature.job.service.port.JobInfoScrapRedisCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class JobInfoScrapRedisCommandRepositoryImpl implements JobInfoScrapRedisCommandRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveScrapInfoToRedis(Long jobInfoId, String userEmail) {
        String scrapRedisKey = JobInfoScrap.makeRedisKey(jobInfoId, userEmail);

        redisTemplate.opsForValue().set(scrapRedisKey, JobInfoScrapType.SCRAP.name(), Duration.ofSeconds(30));
    }

    @Override
    public void unScrapInfoFromRedis(Long jobInfoId, String userEmail) {
        String scrapRedisKey = JobInfoScrap.makeRedisKey(jobInfoId, userEmail);

        redisTemplate.delete(scrapRedisKey);
        redisTemplate.opsForValue().set(scrapRedisKey, JobInfoScrapType.UN_SCRAP.name(), Duration.ofSeconds(30));
    }

    @Override
    public void notCacheUnScrapInfoToRedis(Long jobInfoId, String userEmail) {
        String scrapRedisKey = JobInfoScrap.makeRedisKey(jobInfoId, userEmail);

        redisTemplate.opsForValue().set(scrapRedisKey, JobInfoScrapType.UN_SCRAP.name(), Duration.ofSeconds(30));

    }

    @Override
    public void incrementScrapCountToRedis(Long jobInfoId) {
        String redisKey = JobInfo.makeScrapCountRedisKey(jobInfoId);
        redisTemplate.opsForValue().increment(redisKey);

        redisTemplate.expire(redisKey, Duration.ofSeconds(30));
    }

    @Override
    public void decrementScrapCountFromRedis(Long jobInfoId) {
        String redisKey = JobInfo.makeScrapCountRedisKey(jobInfoId);
        redisTemplate.opsForValue().decrement(redisKey);
        redisTemplate.expire(redisKey, Duration.ofSeconds(20));
    }

    @Override
    public void deleteScrapCountToRedis(Long jobInfoId) {
        String redisKey = JobInfo.makeScrapCountRedisKey(jobInfoId);
        redisTemplate.delete(redisKey);
    }

    @Override
    public void deleteScrapInfo(Long jobInfoId, String userEmail) {
        String scrapRedisKey = JobInfoScrap.makeRedisKey(jobInfoId, userEmail);

        redisTemplate.delete(scrapRedisKey);
    }

}