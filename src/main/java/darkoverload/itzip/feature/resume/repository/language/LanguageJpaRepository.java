package darkoverload.itzip.feature.resume.repository.language;

import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import darkoverload.itzip.feature.resume.repository.language.custom.CustomLanguageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageJpaRepository extends JpaRepository<LanguageEntity, Long> , CustomLanguageRepository {

}
