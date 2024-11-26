package darkoverload.itzip.feature.resume.service.resume.port.language;

import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;

import java.util.List;

public interface LanguageRepository {

    List<Language> update(List<Language> languages);

    Language save(Language language);

    List<Language> saveAll(List<Language> languages);

    void deleteAllById(List<Long> ids);

    void deleteAllLanguages(List<Language> deleteLanguages);

}
