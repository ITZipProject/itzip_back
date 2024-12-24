package darkoverload.itzip.feature.resume.repository.resume;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.service.resume.port.resume.ResumeCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ResumeCommandRepositoryImpl implements ResumeCommandRepository {

    private final ResumeCommandJpaRepository repository;

    @Override
    public Resume save(Resume resume) {
        return repository.save(resume.toEntity()).convertToDomain();
    }

    @Override
    public Resume update(Resume resume) {
        return save(resume);
    }

    @Override
    public void delete(Resume resume) {
        repository.delete(resume.toEntity());
    }

}
