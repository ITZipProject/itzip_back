package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.resume.controller.response.GetResumeDetailsResponse;
import darkoverload.itzip.feature.resume.controller.response.SearchResumeResponse;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResumeReadService {
    List<SearchResumeResponse> searchResumeInfos(String search, Pageable pageable);

}
