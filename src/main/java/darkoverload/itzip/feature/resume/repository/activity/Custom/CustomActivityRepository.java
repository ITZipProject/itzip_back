package darkoverload.itzip.feature.resume.repository.activity.Custom;

import darkoverload.itzip.feature.resume.entity.ActivityEntity;

import java.util.List;

public interface CustomActivityRepository {

    public List<ActivityEntity> findAllByResumeId(Long resumeId);

}
