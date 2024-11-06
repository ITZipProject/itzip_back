package darkoverload.itzip.feature.resume.repository.achievement;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import darkoverload.itzip.feature.resume.service.resume.port.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class AchievementRepositoryImpl implements AchievementRepository {

    private final AchievementJpaRepository repository;

    @Override
    public List<Achievement> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(AchievementEntity::convertToDomain).toList();
    }

    @Override
    public Achievement save(Achievement achievement) {
        return repository.save(achievement.toEntity()).convertToDomain();
    }

    @Override
    public List<Achievement> update(List<Achievement> achievements, Resume resume) {
        List<Long> deleteAchievements = getDeleteAchievementIds(achievements, resume);

        if (!deleteAchievements.isEmpty()) {
            deleteAllById(deleteAchievements);
        }

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

    private List<Long> getDeleteAchievementIds(List<Achievement> achievements, Resume resume) {
        List<Long> achievementIds = getAchievementIds(resume.getResumeId());

        List<Long> updateIds = getUpdateAchievementIds(achievements);

        return achievementIds.stream()
                .filter(id -> !updateIds.contains(id)).toList();
    }

    private List<Long> getUpdateAchievementIds(List<Achievement> achievements) {
        return achievements.stream().filter(Objects::nonNull).map(Achievement::getAchievementId).toList();
    }

    private List<Long> getAchievementIds(Long resumeId) {
        return findAllByResumeId(resumeId).stream().map(Achievement::getAchievementId).toList();
    }


}
