package darkoverload.itzip.feature.resume.service.resume.port.activity;

import darkoverload.itzip.feature.resume.domain.activity.Activity;
import darkoverload.itzip.feature.resume.domain.resume.Resume;

import java.util.List;

public interface ActivityRepository {
    Activity save(Activity activity);

    List<Activity> update(List<Activity> activities);

    List<Activity> saveAll(List<Activity> activities);

    void deleteAllActivities(List<Activity> deleteActivities);

}
