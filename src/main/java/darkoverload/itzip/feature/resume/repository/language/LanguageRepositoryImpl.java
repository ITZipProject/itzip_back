package darkoverload.itzip.feature.resume.repository.language;

import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import darkoverload.itzip.feature.resume.service.resume.port.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class LanguageRepositoryImpl implements LanguageRepository {

    private final LanguageJpaRepository repository;

    @Override
    public List<Language> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(LanguageEntity::convertToDomain).toList();
    }

    @Override
    public List<Language> update(List<Language> languages, Resume resume) {
        List<Long> deleteLanguages = getLanguageDeleteIds(languages, resume);

        if (!deleteLanguages.isEmpty()) {
            deleteAllById(deleteLanguages);
        }

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
    public List<Language> saveAll(List<Language> languages) {
        List<LanguageEntity> languageEntities = languages.stream().map(Language::toEntity).toList();
        return repository.saveAll(languageEntities).stream().map(LanguageEntity::convertToDomain).toList();
    }

    private List<Long> getUpdateLanguageIds(List<Language> languages) {
        return languages.stream().filter(Objects::nonNull).map(Language::getLanguageId).toList();
    }


    private List<Long> getLanguageIds(Long resumeId) {
        return findAllByResumeId(resumeId).stream().map(Language::getLanguageId).toList();
    }

    private List<Long> getLanguageDeleteIds(List<Language> languages, Resume resume) {
        List<Long> languageIds = getLanguageIds(resume.getResumeId());

        List<Long> updateIds = getUpdateLanguageIds(languages);

        return languageIds.stream()
                .filter(id -> !updateIds.contains(id)).toList();
    }

}
