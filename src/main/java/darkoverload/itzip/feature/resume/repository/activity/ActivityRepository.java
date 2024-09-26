package darkoverload.itzip.feature.resume.repository.activity;


import darkoverload.itzip.feature.resume.domain.activity.Activity;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ActivityRepository {

    private final JPAActivityRepository repository;


    public List<Activity> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(ActivityEntity::convertToDomain).toList();
    }

    public List<Activity> update(List<Activity> activities, Resume resume){
        List<Long> activityIds = findAllByResumeId(resume.getResumeId()).stream().map(Activity::getActivityId).toList();

        List<Long> updateIds = activities.stream().filter(Objects::nonNull).map(Activity::getActivityId).toList();


        List<Long> deleteActivities = activityIds.stream()
                .filter(id-> !updateIds.contains(id)).toList();

        if(!deleteActivities.isEmpty()) repository.deleteAllById(deleteActivities);

        return saveAll(activities.stream().map(Activity::toEntity).toList());
    }


    public List<Activity> saveAll(List<ActivityEntity> activityEntities){
        return repository.saveAll(activityEntities).stream().map(ActivityEntity::convertToDomain).toList();
    }

}
