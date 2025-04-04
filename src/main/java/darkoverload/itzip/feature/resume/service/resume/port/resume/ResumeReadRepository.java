package darkoverload.itzip.feature.resume.service.resume.port.resume;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResumeReadRepository {

    List<Resume> searchResumeInfos(String search, Pageable pageable);

    Resume getReferenceById(Long id);

    ResumeEntity getEntityById(Long resumeId);
}
