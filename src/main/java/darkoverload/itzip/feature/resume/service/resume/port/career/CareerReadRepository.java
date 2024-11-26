package darkoverload.itzip.feature.resume.service.resume.port.career;

import darkoverload.itzip.feature.resume.domain.career.Career;

import java.util.List;

public interface CareerReadRepository {
    List<Career> findAllByResumeId(Long resumeId);

}
