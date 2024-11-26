package darkoverload.itzip.feature.resume.service.resume.port.qualification;

import darkoverload.itzip.feature.resume.domain.qualification.Qualification;

import java.util.List;

public interface QualificationReadRepository {
    List<Qualification> findAllByResumeId(Long resumeId);

}
