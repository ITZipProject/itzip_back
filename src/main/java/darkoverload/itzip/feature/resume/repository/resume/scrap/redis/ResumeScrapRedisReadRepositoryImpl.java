package darkoverload.itzip.feature.resume.repository.resume.scrap.redis;

import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrapType;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrap;
import darkoverload.itzip.feature.resume.service.resume.port.resume.redis.ResumeScrapRedisReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class ResumeScrapRedisReadRepositoryImpl implements ResumeScrapRedisReadRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Set<String> getResumeScrapListFromRedis() {
        String redisKey = ResumeScrap.MAP_RESUME_SCRAP_KEY + "*";

        return redisTemplate.keys(redisKey);
    }

    @Override
    public String getResumeStatus(Long resumeId, String userEmail) {
        String redisKey = ResumeScrap.makeRedisKey(resumeId, userEmail);

        return Objects.requireNonNull(redisTemplate.opsForValue().get(redisKey)).toString();
    }

    @Override
    public boolean hasSameResumeScrap(Long resumeId, String userEmail) {
        String redisKey = ResumeScrap.makeRedisKey(resumeId, userEmail);
        return Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
    }

    @Override
    public boolean isResumeScrapStatus(Long resumeId, String userEmail) {
        String redisKey = ResumeScrap.makeRedisKey(resumeId, userEmail);
        String scrapStatus = (String) redisTemplate.opsForValue().get(redisKey);

        if(scrapStatus == null) {
            return false;
        }

        return !scrapStatus.equals(JobInfoScrapType.UN_SCRAP.name());
    }

    @Override
    public String getResumeScrapCount(Long resumeId) {
        String redisKey = Resume.makeScrapCountRedisKey(resumeId);
        return Objects.requireNonNull(redisTemplate.opsForValue().get(redisKey)).toString();
    }

}
