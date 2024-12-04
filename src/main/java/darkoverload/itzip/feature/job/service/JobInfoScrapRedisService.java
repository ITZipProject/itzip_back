package darkoverload.itzip.feature.job.service;

import darkoverload.itzip.feature.job.controller.request.JobInfoScrapRequest;

import java.util.Set;

public interface JobInfoScrapRedisService {

    String jobInfoScrapToRedis(JobInfoScrapRequest request);

    void jobInfoScrapDeleteToRedis(Long jobInfoId, String email);

    Set<String> getScrapKeysFromRedis();

    String getJobInfoScrapCountFromRedis(Long jobInfoId);

    String getJobInfoStatusFromRedis(Long jobInfoId, String email);

}
