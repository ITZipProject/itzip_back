package darkoverload.itzip.feature.resume.repository.language;

import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageReadJpaRepository extends JpaRepository<LanguageEntity, Long> {
    List<LanguageEntity> findAllByResumeId(Long resumeId);

}
