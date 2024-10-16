package darkoverload.itzip.feature.resume.repository.resume;

import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import darkoverload.itzip.feature.resume.repository.resume.custom.CustomResumeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeJpaRepository extends JpaRepository<ResumeEntity, Long> , CustomResumeRepository {

}
