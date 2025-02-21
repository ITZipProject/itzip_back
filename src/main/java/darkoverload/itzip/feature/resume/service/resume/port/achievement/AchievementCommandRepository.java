package darkoverload.itzip.feature.resume.service.resume.port.achievement;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;

import java.util.List;

public interface AchievementCommandRepository {
    Achievement save(Achievement achievement);

    List<Achievement> update(List<Achievement> achievements);

    List<Achievement> saveAll(List<Achievement> achievements);

    void deleteAllById(List<Long> ids);

    void deleteAllAchievements(List<Achievement> deleteAchievements);

}
