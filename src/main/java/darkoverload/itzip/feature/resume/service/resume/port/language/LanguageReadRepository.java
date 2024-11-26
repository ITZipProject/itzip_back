package darkoverload.itzip.feature.resume.service.resume.port.language;

import darkoverload.itzip.feature.resume.domain.language.Language;

import java.util.List;

public interface LanguageReadRepository {
    List<Language> findAllByResumeId(Long resumeId);
}
