package darkoverload.itzip.feature.resume.repository.resume.scrap.redis;

import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrapType;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrap;
import darkoverload.itzip.feature.resume.service.resume.port.resume.redis.ResumeScrapRedisCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class ResumeScrapRedisCommandImpl implements ResumeScrapRedisCommandRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveResumeScrapToRedis(Long resumeId, String userEmail) {
        String scrapRedisKey = ResumeScrap.makeRedisKey(resumeId, userEmail);

        redisTemplate.opsForValue().set(scrapRedisKey, JobInfoScrapType.SCRAP.name(), Duration.ofSeconds(30));
    }

    @Override
    public void incrementScrapCountToRedis(Long resumeId) {
        String redisKey = Resume.makeScrapCountRedisKey(resumeId);
        redisTemplate.opsForValue().increment(redisKey);

        redisTemplate.expire(redisKey, Duration.ofSeconds(30));
    }

    @Override
    public void decrementScrapCountFromRedis(Long resumeId) {
        String redisKey = Resume.makeScrapCountRedisKey(resumeId);
        redisTemplate.opsForValue().decrement(redisKey);
        redisTemplate.expire(redisKey, Duration.ofSeconds(20));
    }

    @Override
    public void unResumeScrapFromRedis(Long resumeId, String userEmail) {
        String scrapRedisKey = ResumeScrap.makeRedisKey(resumeId, userEmail);

        redisTemplate.delete(scrapRedisKey);
        redisTemplate.opsForValue().set(scrapRedisKey, JobInfoScrapType.UN_SCRAP.name(), Duration.ofSeconds(30));
    }

    @Override
    public void deleteResumeInfo(Long resumeId, String userEmail) {
        String scrapRedisKey = ResumeScrap.makeRedisKey(resumeId, userEmail);

        redisTemplate.delete(scrapRedisKey);
    }

    @Override
    public void deleteResumeCountToRedis(Long resumeId) {
        String redisKey = Resume.makeScrapCountRedisKey(resumeId);

        redisTemplate.delete(redisKey);
    }


}
