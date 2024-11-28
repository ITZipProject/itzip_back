package darkoverload.itzip.feature.resume.repository.education;

import darkoverload.itzip.feature.resume.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationReadJpaRepository extends JpaRepository<EducationEntity, Long> {
    List<EducationEntity> findAllByResumeId(Long resumeId);

}
