package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.resume.controller.response.GetResumeDetailsResponse;
import darkoverload.itzip.feature.resume.controller.response.SearchResumeResponse;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrap;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResumeReadService {
    List<SearchResumeResponse> searchResumeInfos(String search, Pageable pageable);


    GetResumeDetailsResponse getResumeDetails(Long id, CustomUserDetails user);

    Resume getReferenceById(Long resumeId);

    ResumeScrap findByResumeScrap(Long resumeId, String userEmail);

    ResumeEntity getById(Long resumeId);

}
