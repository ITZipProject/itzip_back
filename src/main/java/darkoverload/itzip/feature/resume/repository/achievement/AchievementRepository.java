package darkoverload.itzip.feature.resume.repository.achievement;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class AchievementRepository {

    private final JPAAchievementRepository repository;


    public List<Achievement> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(AchievementEntity::convertToDomain).toList();
    }

    public Achievement save(Achievement achievement) {
        return repository.save(achievement.toEntity()).convertToDomain();
    }

    public List<Achievement> update(List<Achievement> achievements, Resume resume){
        List<Long> achievementIds = findAllByResumeId(resume.getResumeId()).stream().map(Achievement::getAchievementId).toList();

        List<Long> updateIds = achievements.stream().filter(Objects::nonNull).map(Achievement::getAchievementId).toList();

        List<Long> deleteAchievements = achievementIds.stream()
                .filter(id-> !updateIds.contains(id)).toList();

        if(!deleteAchievements.isEmpty()) repository.deleteAllById(deleteAchievements);

        List<AchievementEntity> result = repository.saveAll(achievements.stream().map(Achievement::toEntity).toList());

        return result.stream().map(AchievementEntity::convertToDomain).toList();
    }

    public void saveAll(List<AchievementEntity> achievementEntities) {
        repository.saveAll(achievementEntities);
    }
}
