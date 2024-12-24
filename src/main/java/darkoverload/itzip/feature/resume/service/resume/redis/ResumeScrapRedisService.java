package darkoverload.itzip.feature.resume.service.resume.redis;

import darkoverload.itzip.feature.resume.controller.request.ResumeInfoScrapRequest;

import java.util.Set;

public interface ResumeScrapRedisService {

    String resumeScrapToRedis(ResumeInfoScrapRequest request);

    Set<String> getScrapKeysFromRedis();

    String getResumeStatusFromRedis(Long resumeId, String userEmail);

    String getJobInfoScrapCountFromRedis(Long resumeId);

    void resumeScrapDeleteToRedis(Long resumeId, String userEmail);

}
