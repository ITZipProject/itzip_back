package darkoverload.itzip.feature.resume.repository.achievement;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import darkoverload.itzip.feature.resume.service.resume.port.achievement.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AchievementRepositoryImpl implements AchievementRepository {

    private final AchievementJpaRepository repository;

    @Override
    public Achievement save(Achievement achievement) {
        return repository.save(achievement.toEntity()).convertToDomain();
    }

    @Override
    public List<Achievement> update(List<Achievement> achievements) {
        return saveAll(achievements);
    }

    @Override
    public List<Achievement> saveAll(List<Achievement> achievements) {
        List<AchievementEntity> achievementEntities = achievements.stream().map(Achievement::toEntity).toList();
        return repository.saveAll(achievementEntities).stream().map(AchievementEntity::convertToDomain).toList();
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAllAchievements(List<Achievement> deleteAchievements) {
        repository.deleteAll(deleteAchievements.stream().map(Achievement::toEntity).toList());
    }

}
