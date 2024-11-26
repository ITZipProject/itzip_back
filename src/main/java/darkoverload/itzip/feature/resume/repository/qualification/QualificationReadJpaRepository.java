package darkoverload.itzip.feature.resume.repository.qualification;

import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualificationReadJpaRepository extends JpaRepository<QualificationEntity, Long> {
    List<QualificationEntity> findAllByResumeId(Long resumeId);

}
