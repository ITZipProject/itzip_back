package darkoverload.itzip.feature.resume.service.resume.port.myskill;

import darkoverload.itzip.feature.resume.domain.myskill.MySkill;

import java.util.List;

public interface MySkillReadRepository {
    List<MySkill> findByAllResumeId(Long resumeId);

}
