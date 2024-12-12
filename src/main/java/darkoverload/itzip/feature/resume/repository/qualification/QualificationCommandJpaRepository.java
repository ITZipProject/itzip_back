package darkoverload.itzip.feature.resume.repository.qualification;

import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationCommandJpaRepository extends JpaRepository<QualificationEntity, Long> {

}
