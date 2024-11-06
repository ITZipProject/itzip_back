package darkoverload.itzip.feature.resume.service.resume.port;

import darkoverload.itzip.feature.resume.domain.activity.Activity;
import darkoverload.itzip.feature.resume.domain.resume.Resume;

import java.util.List;

public interface ActivityRepository {
    List<Activity> findAllByResumeId(Long resumeId);

    Activity save(Activity activity);

    List<Activity> update(List<Activity> activities);

    void deleteAllByIds(List<Long> ids);

    List<Activity> saveAll(List<Activity> activities);

    void deleteAllActivities(List<Activity> deleteActivities);

}
