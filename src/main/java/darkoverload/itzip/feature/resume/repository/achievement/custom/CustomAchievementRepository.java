package darkoverload.itzip.feature.resume.repository.achievement.custom;

import darkoverload.itzip.feature.resume.entity.AchievementEntity;

import java.util.List;

public interface CustomAchievementRepository {
    List<AchievementEntity> findAllByResumeId(Long resumeId);
}
