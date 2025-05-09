package darkoverload.itzip.feature.job.service;

import darkoverload.itzip.feature.job.controller.request.JobInfoScrapRequest;
import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface JobInfoService {
    Page<JobInfoSearchResponse> searchJobInfo(String search, String techName, Integer experienceMin, Integer experienceMax, String locationCode, Pageable pageable);

    JobInfo getById(Long jobInfoId);

    void jobInfoScrapSave(JobInfoScrap jobInfoScrap);

    void updateScrapCount(JobInfo jobInfo);

    void delete(JobInfoScrap jobInfoScrap);

    JobInfoScrap findByJobInfoId(Long jobInfoId, String email);

}
