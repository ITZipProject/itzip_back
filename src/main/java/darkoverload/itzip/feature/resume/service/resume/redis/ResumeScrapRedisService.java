package darkoverload.itzip.feature.resume.service.resume.redis;

import darkoverload.itzip.feature.resume.controller.request.ResumeInfoScrapRequest;

public interface ResumeScrapRedisService {

    String resumeScrapToRedis(ResumeInfoScrapRequest request);

}
