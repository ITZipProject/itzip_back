package darkoverload.itzip.feature.resume.repository.qualification.custom;

import darkoverload.itzip.feature.resume.entity.QualificationEntity;

import java.util.List;

public interface CustomQualification {

    List<QualificationEntity> findByAllResumeId(Long resumeId);

}
