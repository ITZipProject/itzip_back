package darkoverload.itzip.feature.job.service;

import darkoverload.itzip.feature.job.controller.request.JobInfoScrapRequest;
import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface JobInfoService {
    Page<JobInfoSearchResponse> searchJobInfo(String search, String techName, Integer experienceMin, Integer experienceMax, String location, Pageable pageable);


    String jobInfoScrap(JobInfoScrapRequest request);

    Set<String> getScrapKeysFromRedis();

    JobInfo getById(Long jobInfoId);

    void jobInfoScrapSave(JobInfoScrap jobInfoScrap);

    void jobInfoScrapDeleteToRedis(Long jobInfoId, String email);

    String getJobInfoScrapCountFromRedis(Long jobInfoId);

    void updateScrapCount(JobInfo jobInfo);

    void delete(JobInfoScrap jobInfoScrap);

    String getJobInfoStatusFromRedis(Long jobInfoId, String email);

    JobInfoScrap findByJobInfoId(Long jobInfoId, String email);

}
