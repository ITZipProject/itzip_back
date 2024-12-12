package darkoverload.itzip.feature.resume.service.resume.port.education;

import darkoverload.itzip.feature.resume.domain.education.Education;

import java.util.List;

public interface EducationCommandRepository {
    List<Education> update(List<Education> educations);

    Education save(Education education);

    void deleteAllById(List<Long> ids);

    List<Education> saveAll(List<Education> educations);

    void deleteAllEducations(List<Education> deleteEducations);

}
