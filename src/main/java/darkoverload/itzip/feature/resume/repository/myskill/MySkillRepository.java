package darkoverload.itzip.feature.resume.repository.myskill;


import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.MySkillEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class MySkillRepository {

    private final JPAMySkillRepository repository;

    public List<MySkill> findByAllResumeId(Long resumeId) {
        return repository.findByAllResumeId(resumeId).stream().map(MySkillEntity::convertToDomain).toList();
    }

    public List<MySkill> update(List<MySkill> mySkills, Resume resume){
        List<Long> mySkillIds = findByAllResumeId(resume.getResumeId()).stream().map(MySkill::getMySkillId).toList();

        List<Long> updateIds = mySkills.stream().filter(Objects::nonNull).map(MySkill::getMySkillId).toList();

        List<Long> deleteMySkills = mySkillIds.stream()
                .filter(id-> !updateIds.contains(id)).toList();

        if(!deleteMySkills.isEmpty()) repository.deleteAllById(deleteMySkills);

        return saveAll(mySkills.stream().map(MySkill::toEntity).toList());
    }

    public List<MySkill> saveAll(List<MySkillEntity> mySkillEntities) {
        return repository.saveAll(mySkillEntities).stream().map(MySkillEntity::convertToDomain).toList();
    }

}
