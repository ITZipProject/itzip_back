package darkoverload.itzip.feature.resume.repository.education;

import darkoverload.itzip.feature.resume.entity.EducationEntity;
import darkoverload.itzip.feature.resume.repository.education.custom.CustomEducationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationJpaRepository extends JpaRepository<EducationEntity, Long> , CustomEducationRepository {
}
