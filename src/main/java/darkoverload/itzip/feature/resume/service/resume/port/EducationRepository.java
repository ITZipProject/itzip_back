package darkoverload.itzip.feature.resume.service.resume.port;

import darkoverload.itzip.feature.resume.domain.education.Education;
import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.feature.resume.domain.resume.Resume;

import java.util.List;

public interface EducationRepository {
    List<Education> findAllByResumeId(Long resumeId);

    List<Education> update(List<Education> educations);

    Education save(Education education);

    void deleteAllById(List<Long> ids);

    List<Education> saveAll(List<Education> educations);

    void deleteAllEducations(List<Education> deleteEducations);

}
