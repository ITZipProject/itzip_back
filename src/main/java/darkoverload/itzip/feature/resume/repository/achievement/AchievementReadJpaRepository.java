package darkoverload.itzip.feature.resume.repository.achievement;

import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementReadJpaRepository extends JpaRepository<AchievementEntity, Long> {
    List<AchievementEntity> findAllByResumeId(Long resumeId);

}
