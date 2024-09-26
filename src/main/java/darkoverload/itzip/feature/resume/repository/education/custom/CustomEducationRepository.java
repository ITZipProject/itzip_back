package darkoverload.itzip.feature.resume.repository.education.custom;

import darkoverload.itzip.feature.resume.entity.EducationEntity;

import java.util.List;

public interface CustomEducationRepository {

    List<EducationEntity> findAllByResumeId(Long resumeId);

}
