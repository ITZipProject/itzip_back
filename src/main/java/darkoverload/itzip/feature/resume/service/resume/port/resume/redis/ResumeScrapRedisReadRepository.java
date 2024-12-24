package darkoverload.itzip.feature.resume.service.resume.port.resume.redis;

import java.util.Set;

public interface ResumeScrapRedisReadRepository {
    Set<String>  getResumeScrapListFromRedis();

    String getResumeStatus(Long resumeId, String userEmail);

    boolean hasSameResumeScrap(Long resumeId, String userEmail);

    boolean isResumeScrapStatus(Long resumeId, String userEmail);

    String getResumeScrapCount(Long resumeId);

}
