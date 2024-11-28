package darkoverload.itzip.feature.resume.repository.activity;

import darkoverload.itzip.feature.resume.domain.activity.Activity;
import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import darkoverload.itzip.feature.resume.service.resume.port.activity.ActivityReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ActivityReadRepositoryImpl implements ActivityReadRepository {

    private final ActivityReadJpaRepository repository;

    @Override
    public List<Activity> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(ActivityEntity::convertToDomain).toList();
    }
}
