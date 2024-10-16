package darkoverload.itzip.feature.resume.service.resume.port;

import darkoverload.itzip.feature.resume.domain.activity.Activity;
import darkoverload.itzip.feature.resume.domain.resume.Resume;

import java.util.List;

public interface ActivityRepository {
    List<Activity> findAllByResumeId(Long resumeId);

    List<Activity> update(List<Activity> activities, Resume resume);

    void deleteAllByIds(List<Long> ids);

    List<Activity> saveAll(List<Activity> activities);
}
