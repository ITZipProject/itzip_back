package darkoverload.itzip.feature.resume.service.resume.port.activity;

import darkoverload.itzip.feature.resume.domain.activity.Activity;

import java.util.List;

public interface ActivityReadRepository {
    List<Activity> findAllByResumeId(Long resumeId);

}
