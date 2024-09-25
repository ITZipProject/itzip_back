package darkoverload.itzip.feature.resume.repository.language;

import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class LanguageRepository {

    private final JPALanguageRepository repository;

    public List<Language> findAllByResumeId(Long resumeId){
        return repository.findAllByResumeId(resumeId).stream().map(LanguageEntity::convertToDomain).toList();
    }


    public List<Language> update(List<Language> languages, Resume resume) {
        List<Long> languageIds = findAllByResumeId(resume.getResumeId()).stream().map(Language::getLanguageId).toList();

        List<Long> updateIds = languages.stream().filter(Objects::nonNull).map(Language::getLanguageId).toList();

        List<Long> deleteLanguages = languageIds.stream()
                .filter(id-> !updateIds.contains(id)).toList();

        if(!deleteLanguages.isEmpty()) repository.deleteAllById(deleteLanguages);

        return saveAll(languages.stream().map(Language::toEntity).toList());
    }

    public List<Language> saveAll(List<LanguageEntity> languages) {
        return repository.saveAll(languages).stream().map(LanguageEntity::convertToDomain).toList();
    }

}
