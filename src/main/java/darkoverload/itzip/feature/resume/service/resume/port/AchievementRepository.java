package darkoverload.itzip.feature.resume.service.resume.port;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import darkoverload.itzip.feature.resume.domain.resume.Resume;

import java.util.List;

public interface AchievementRepository {
    List<Achievement> findAllByResumeId(Long resumeId);

    Achievement save(Achievement achievement);

    List<Achievement> update(List<Achievement> achievements);

    List<Achievement> saveAll(List<Achievement> achievements);

    void deleteAllById(List<Long> ids);

    void deleteAllAchievements(List<Achievement> deleteAchievements);

}
