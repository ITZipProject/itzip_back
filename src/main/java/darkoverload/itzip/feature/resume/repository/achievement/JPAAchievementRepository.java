package darkoverload.itzip.feature.resume.repository.achievement;

import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import darkoverload.itzip.feature.resume.repository.achievement.custom.CustomAchievementRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAAchievementRepository extends JpaRepository<AchievementEntity, Long> , CustomAchievementRepository {


}
