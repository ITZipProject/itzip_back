package darkoverload.itzip.feature.resume.repository.language.custom;


import darkoverload.itzip.feature.resume.entity.LanguageEntity;

import java.util.List;

public interface CustomLanguageRepository {

    List<LanguageEntity> findAllByResumeId(Long resumeId);

}
