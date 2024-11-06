package darkoverload.itzip.feature.resume.service.resume.port;

import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;

import java.util.List;

public interface LanguageRepository {
    List<Language> findAllByResumeId(Long resumeId);

    List<Language> update(List<Language> languages, Resume resume);

    Language save(Language language);

    List<Language> saveAll(List<Language> languages);

    void deleteAllById(List<Long> ids);
}
