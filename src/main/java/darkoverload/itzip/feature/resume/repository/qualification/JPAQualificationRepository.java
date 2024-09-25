package darkoverload.itzip.feature.resume.repository.qualification;

import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import darkoverload.itzip.feature.resume.repository.qualification.custom.CustomQualification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAQualificationRepository extends JpaRepository<QualificationEntity, Long> , CustomQualification {

}
