package darkoverload.itzip.feature.resume.service.resume.port.resume;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResumeRepository {
    Resume save(Resume resume);

    Resume update(Resume resume);

}
