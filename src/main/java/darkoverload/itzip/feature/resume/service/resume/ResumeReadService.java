package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResumeReadService {
    List<Resume> searchResumeInfos(String search, Pageable pageable);

}
