package darkoverload.itzip.feature.resume.service.resume.port.resume;

import darkoverload.itzip.feature.resume.domain.resume.Resume;

public interface ResumeRepository {
    Resume save(Resume resume);

    Resume update(Resume resume);

    Resume findById(long id);
}
