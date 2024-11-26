package darkoverload.itzip.feature.resume.repository.myskill;

import darkoverload.itzip.feature.resume.entity.MySkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MySkillReadJpaRepository extends JpaRepository<MySkillEntity, Long> {
    List<MySkillEntity> findAllByResumeId(Long resumeId);
}
