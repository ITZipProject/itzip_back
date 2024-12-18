package darkoverload.itzip.feature.resume.repository.resume;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.service.resume.port.resume.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ResumeRepositoryImpl implements ResumeRepository {

    private final ResumeJpaRepository repository;

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
