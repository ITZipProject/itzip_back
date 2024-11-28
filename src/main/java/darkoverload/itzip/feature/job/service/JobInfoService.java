package darkoverload.itzip.feature.job.service;

import darkoverload.itzip.feature.job.controller.request.JobInfoScrapRequest;
import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobInfoService {
    Page<JobInfoSearchResponse> searchJobInfo(String search, String techName, Integer experienceMin, Integer experienceMax, String location, Pageable pageable);

    String jobInfoScrap(JobInfoScrapRequest request);
}
