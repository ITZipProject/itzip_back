package darkoverload.itzip.feature.resume.repository.myskill;

import darkoverload.itzip.feature.resume.entity.MySkillEntity;
import darkoverload.itzip.feature.resume.repository.myskill.custom.CustomMySkillRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySkillJpaRepository extends JpaRepository<MySkillEntity, Long> , CustomMySkillRepository {

}
