package darkoverload.itzip.feature.resume.service.resume.port.resume.redis;

public interface ResumeScrapRedisCommandRepository {

    void saveResumeScrapToRedis(Long resumeId, String userEmail);

    void incrementScrapCountToRedis(Long resumeId);

    void decrementScrapCountFromRedis(Long resumeId);

    void unResumeScrapFromRedis(Long resumeId, String userEmail);

    void deleteResumeInfo(Long resumeId, String userEmail);

    void deleteResumeCountToRedis(Long resumeId);

}
