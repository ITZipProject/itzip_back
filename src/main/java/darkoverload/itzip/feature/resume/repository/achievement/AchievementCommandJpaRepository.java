package darkoverload.itzip.feature.resume.repository.achievement;

import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementCommandJpaRepository extends JpaRepository<AchievementEntity, Long> {

}
