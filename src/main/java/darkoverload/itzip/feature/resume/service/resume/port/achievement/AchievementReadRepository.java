package darkoverload.itzip.feature.resume.service.resume.port.achievement;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;

import java.util.List;

public interface AchievementReadRepository {
    List<Achievement> findAllByResumeId(Long resumeId);

}
