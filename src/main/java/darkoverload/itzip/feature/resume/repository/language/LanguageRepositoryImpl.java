package darkoverload.itzip.feature.resume.repository.language;

import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class LanguageRepositoryImpl implements LanguageRepository {

    private final LanguageJpaRepository repository;

    @Override
    public List<Language> update(List<Language> languages) {
        return saveAll(languages);
    }

    @Override
    public Language save(Language language) {
        return repository.save(language.toEntity()).convertToDomain();
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAllLanguages(List<Language> deleteLanguages) {
        repository.deleteAll(deleteLanguages.stream().map(Language::toEntity).toList());
    }

    @Override
    public List<Language> saveAll(List<Language> languages) {
        List<LanguageEntity> languageEntities = languages.stream().map(Language::toEntity).toList();
        return repository.saveAll(languageEntities).stream().map(LanguageEntity::convertToDomain).toList();
    }

}
