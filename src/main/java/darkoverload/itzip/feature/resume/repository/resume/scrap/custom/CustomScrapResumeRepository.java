package darkoverload.itzip.feature.resume.repository.resume.scrap.custom;

import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrap;

import java.util.Optional;

public interface CustomScrapResumeRepository {
    Optional<ResumeScrap> findByResumeScrap(Long resumeId, String userEmail);
}
