package darkoverload.itzip.feature.resume.repository.myskill;

import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
import darkoverload.itzip.feature.resume.entity.MySkillEntity;
import darkoverload.itzip.feature.resume.service.resume.port.myskill.MySkillReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MySkillReadRepositoryImpl implements MySkillReadRepository {

    private final MySkillReadJpaRepository repository;

    @Override
    public List<MySkill> findByAllResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(MySkillEntity::convertToDomain).toList();
    }

}
