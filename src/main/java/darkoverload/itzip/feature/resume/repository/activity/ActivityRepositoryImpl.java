package darkoverload.itzip.feature.resume.repository.activity;


import darkoverload.itzip.feature.resume.domain.activity.Activity;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import darkoverload.itzip.feature.resume.service.resume.port.activity.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepository {

    private final ActivityJpaRepository repository;

    @Override
    public Activity save(Activity activity) {

        return repository.save(activity.toEntity()).convertToDomain();
    }

    @Override
    public List<Activity> update(List<Activity> activities) {
        return saveAll(activities);
    }

    @Override
    public List<Activity> saveAll(List<Activity> activities) {
        List<ActivityEntity> activityEntities = activities.stream().map(Activity::toEntity).toList();
        return repository.saveAll(activityEntities).stream().map(ActivityEntity::convertToDomain).toList();
    }

    @Override
    public void deleteAllActivities(List<Activity> deleteActivities) {
        repository.deleteAll(deleteActivities.stream().map(Activity::toEntity).toList());
    }

}
