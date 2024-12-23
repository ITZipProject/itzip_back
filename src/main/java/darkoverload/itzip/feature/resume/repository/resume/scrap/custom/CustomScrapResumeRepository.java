package darkoverload.itzip.feature.resume.repository.resume.scrap.custom;

import darkoverload.itzip.feature.resume.entity.resume.ResumeScrapEntity;

import java.util.Optional;

public interface CustomScrapResumeRepository {
    Optional<ResumeScrapEntity> findByResumeScrap(Long userId, Long resumeId);
}
