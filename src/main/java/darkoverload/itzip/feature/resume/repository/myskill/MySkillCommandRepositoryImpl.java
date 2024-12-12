package darkoverload.itzip.feature.resume.repository.myskill;


import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
import darkoverload.itzip.feature.resume.entity.MySkillEntity;
import darkoverload.itzip.feature.resume.service.resume.port.myskill.MySkillCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MySkillCommandRepositoryImpl implements MySkillCommandRepository {

    private final MySkillCommandJpaRepository repository;

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

}
