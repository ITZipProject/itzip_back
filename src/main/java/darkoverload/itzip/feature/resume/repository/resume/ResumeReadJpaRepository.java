package darkoverload.itzip.feature.resume.repository.resume;

import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import darkoverload.itzip.feature.resume.repository.resume.custom.CustomResumeReadRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeReadJpaRepository extends JpaRepository<ResumeEntity, Long>, CustomResumeReadRepository {
    List<ResumeEntity> searchResumeInfos(String search, Pageable pageable);

}
