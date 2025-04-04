package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.controller.request.UpdateResumeRequest;
import darkoverload.itzip.feature.resume.controller.response.CreateResumeResponse;
import darkoverload.itzip.feature.resume.controller.response.UpdateResumeResponse;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrap;

public interface ResumeCommandService {
    CreateResumeResponse create(CreateResumeRequest request, CustomUserDetails user);

    UpdateResumeResponse update(UpdateResumeRequest request, CustomUserDetails user);

    void delete(Long id, CustomUserDetails user);

    void scrapDelete(ResumeScrap resumeScrap);

    void updateScrapCount(Resume resume);

    void resumeScrapSave(ResumeScrap resumeScrap);

}
