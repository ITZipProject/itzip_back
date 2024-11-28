package darkoverload.itzip.feature.resume.repository.language;

import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LanguageReadRepositoryImpl implements LanguageReadRepository {

    private final LanguageReadJpaRepository repository;

    @Override
    public List<Language> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(LanguageEntity::convertToDomain).toList();
    }
}

