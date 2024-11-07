package darkoverload.itzip.feature.resume.repository.myskill;


import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.MySkillEntity;
import darkoverload.itzip.feature.resume.service.resume.port.MySkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MySkillRepositoryImpl implements MySkillRepository {

    private final MySkillJpaRepository repository;

    @Override
    public List<MySkill> findByAllResumeId(Long resumeId) {
        return repository.findByAllResumeId(resumeId).stream().map(MySkillEntity::convertToDomain).toList();
    }

    @Override
    public List<MySkill> update(List<MySkill> mySkills){
        return saveAll(mySkills);
    }

    @Override
    public MySkill save(MySkill mySkill) {
        return repository.save(mySkill.toEntity()).convertToDomain();
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAllMySkills(List<MySkill> allMySkills) {
        repository.deleteAll(allMySkills.stream().map(MySkill::toEntity).toList());
    }

    @Override
    public List<MySkill> saveAll(List<MySkill> mySkill) {
        List<MySkillEntity> mySkillEntities = repository.saveAll(mySkill.stream().map(MySkill::toEntity).toList());
        return mySkillEntities.stream().map(MySkillEntity::convertToDomain).collect(Collectors.toList());
    }

    private List<Long> getMySkillDeleteIds(List<MySkill> mySkills, Resume resume) {
        List<Long> mySkillIds = getMySkillIds(resume.getResumeId());

        List<Long> updateIds = getMySkillUpdateIds(mySkills);

        return mySkillIds.stream()
                .filter(id -> !updateIds.contains(id)).toList();
    }


    private List<Long> getMySkillUpdateIds(List<MySkill> mySkills) {
        return mySkills.stream().filter(Objects::nonNull).map(MySkill::getMySkillId).toList();
    }

    private List<Long> getMySkillIds(Long resumeId){
        return findByAllResumeId(resumeId).stream().map(MySkill::getMySkillId).toList();
    }
}
