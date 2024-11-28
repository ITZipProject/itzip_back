package darkoverload.itzip.feature.resume.repository.achievement;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import darkoverload.itzip.feature.resume.service.resume.port.achievement.AchievementReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AchievementReadRepositoryImpl implements AchievementReadRepository {

    private final AchievementReadJpaRepository repository;

    @Override
    public List<Achievement> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(AchievementEntity::convertToDomain).toList();
    }

}
