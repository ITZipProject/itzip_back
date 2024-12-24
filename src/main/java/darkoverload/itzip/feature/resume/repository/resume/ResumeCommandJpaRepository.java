package darkoverload.itzip.feature.resume.repository.resume;

import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeCommandJpaRepository extends JpaRepository<ResumeEntity, Long> {

}
