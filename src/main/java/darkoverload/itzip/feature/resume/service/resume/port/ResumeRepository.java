package darkoverload.itzip.feature.resume.service.resume.port;

import darkoverload.itzip.feature.resume.domain.resume.Resume;

public interface ResumeRepository {
    Resume save(Resume resume);

    Resume update(Resume resume);

    Resume getReferenceById(Long id);
}
