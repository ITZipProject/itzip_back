package darkoverload.itzip.feature.resume.service.resume.port.education;

import darkoverload.itzip.feature.resume.domain.education.Education;

import java.util.List;

public interface EducationReadRepository {
    List<Education> findAllByResumeId(Long resumeId);
}
