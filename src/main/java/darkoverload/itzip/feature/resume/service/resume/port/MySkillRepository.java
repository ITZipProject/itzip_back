package darkoverload.itzip.feature.resume.service.resume.port;

import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
import darkoverload.itzip.feature.resume.domain.resume.Resume;

import java.util.List;

public interface MySkillRepository {
    List<MySkill> findByAllResumeId(Long resumeId);

    List<MySkill> update(List<MySkill> mySkills, Resume resume);

    List<MySkill> saveAll(List<MySkill> mySkill);

    void deleteAllById(List<Long> ids);
}
