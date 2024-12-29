package darkoverload.itzip.feature.job.repository.redis;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrapType;
import darkoverload.itzip.feature.job.service.port.redis.JobInfoScrapRedisReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class JobInfoScrapRedisReadRepositoryImpl implements JobInfoScrapRedisReadRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean hasSameJobInfoScrap(Long jobInfoId, String userEmail) {
        String redisKey = JobInfoScrap.makeRedisKey(jobInfoId, userEmail);
        return Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
    }

    @Override
    public boolean isJobInfoScrapStatus(Long jobInfoId, String userEmail) {
        String redisKey = JobInfoScrap.makeRedisKey(jobInfoId, userEmail);
        String scrapStatus = (String) redisTemplate.opsForValue().get(redisKey);

        if(scrapStatus == null) {
            return false;
        }

        return !scrapStatus.equals(JobInfoScrapType.UN_SCRAP.name());
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

    @Override
    public String getJobInfoStatus(Long jobInfoId, String email) {
        String redisKey = JobInfoScrap.makeRedisKey(jobInfoId, email);
        return Objects.requireNonNull(redisTemplate.opsForValue().get(redisKey)).toString();
    }

}
