package darkoverload.itzip.feature.resume.repository.activity;


import darkoverload.itzip.feature.resume.domain.activity.Activity;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import darkoverload.itzip.feature.resume.service.resume.port.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepository {

    private final ActivityJpaRepository repository;

    @Override
    public List<Activity> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(ActivityEntity::convertToDomain).toList();
    }

    @Override
    public Activity save(Activity activity) {

        return repository.save(activity.toEntity()).convertToDomain();
    }

    @Override
    public List<Activity> update(List<Activity> activities) {
        return saveAll(activities);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        repository.deleteAllById(ids);
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

    private List<Long> getDeleteActivityIds(List<Activity> activities, Resume resume) {
        List<Long> activityIds = getActivityIds(resume.getResumeId());
        List<Long> updateIds = getUpdateActivityIds(activities);

        return activityIds.stream()
                .filter(id -> !updateIds.contains(id)).toList();
    }

    private List<Long> getUpdateActivityIds(List<Activity> activities) {
        return activities.stream().filter(Objects::nonNull).map(Activity::getActivityId).toList();
    }

    private List<Long> getActivityIds(Long resumeId) {
        return findAllByResumeId(resumeId).stream().map(Activity::getActivityId).toList();
    }

}
