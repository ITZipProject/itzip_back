package darkoverload.itzip.feature.resume.service.resume.port.myskill;

import darkoverload.itzip.feature.resume.domain.myskill.MySkill;

import java.util.List;

public interface MySkillCommandRepository {
    List<MySkill> update(List<MySkill> mySkills);

    MySkill save(MySkill mySkill);

    List<MySkill> saveAll(List<MySkill> mySkill);

    void deleteAllById(List<Long> ids);

    void deleteAllMySkills(List<MySkill> allMySkills);

}
