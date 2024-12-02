package darkoverload.itzip.feature.job.repository;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrapType;
import darkoverload.itzip.feature.job.service.port.JobInfoScrapRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JobInfoScrapRedisRepositoryImpl implements JobInfoScrapRedisRepository {

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

    public boolean hasSameJobInfoScrap(Long jobInfoId, String userEmail) {
        String redisKey = JobInfoScrap.makeRedisKey(jobInfoId, userEmail);
        return redisTemplate.hasKey(redisKey);
    }

    @Override
    public void incrementScrapCountToRedis(Long jobInfoId) {
        String redisKey = JobInfo.makeScrapCountRedisKey(jobInfoId);
        redisTemplate.opsForValue().increment(redisKey);

        System.out.println("redisKey :: " + redisKey);
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

    @Override
    public boolean isJobInfoScrapStatus(Long jobInfoId, String userEmail) {
        String redisKey = JobInfoScrap.makeRedisKey(jobInfoId, userEmail);
        String scrapStatus = (String) redisTemplate.opsForValue().get(redisKey);

        if(scrapStatus == null) {
            return false;
        }

        if(scrapStatus.equals(JobInfoScrapType.UN_SCRAP.name())) {
            return false;
        }

        return true;
    }

    @Override
    public Set<String> getJobInfoScrapListFromRedis() {
        String redisKey = JobInfoScrap.MAP_JOB_SCRAP_KEY + "*";

        return redisTemplate.keys(redisKey);
    }

    @Override
    public String getJobInfoScrapCount(Long jobInfoId) {
        String redisKey = JobInfo.makeScrapCountRedisKey(jobInfoId);

        return Objects.requireNonNull(redisTemplate.opsForValue().get(redisKey)).toString();
    }

    public String getJobInfoStatus(Long jobInfoId, String email) {
        String redisKey = JobInfoScrap.makeRedisKey(jobInfoId, email);
        return Objects.requireNonNull(redisTemplate.opsForValue().get(redisKey)).toString();
    }

}