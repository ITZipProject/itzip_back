package darkoverload.itzip.feature.resume.repository.myskill.custom;

import darkoverload.itzip.feature.resume.entity.MySkillEntity;

import java.util.List;

public interface CustomMySkillRepository {

    List<MySkillEntity> findByAllResumeId(Long resumeId);

}
