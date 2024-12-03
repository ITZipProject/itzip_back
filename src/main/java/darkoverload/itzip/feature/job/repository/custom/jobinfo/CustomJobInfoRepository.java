package darkoverload.itzip.feature.job.repository.custom.jobinfo;

import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CustomJobInfoRepository {
    long bulkDeleteByPositionIds(List<Long> positionIds);

    Page<JobInfoSearchResponse> searchJobInfo(String search, String category, Integer experienceMin, Integer experienceMax, Pageable pageable);

}
