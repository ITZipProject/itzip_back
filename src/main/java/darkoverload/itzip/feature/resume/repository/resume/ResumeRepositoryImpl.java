package darkoverload.itzip.feature.resume.repository.resume;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.service.resume.port.resume.ResumeReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.resume.ResumeRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ResumeRepositoryImpl implements ResumeRepository {

    private final ResumeJpaRepository repository;
    private final ResumeReadRepository resumeReadRepository;
    @Override
    public Resume save(Resume resume) {
        return repository.save(resume.toEntity()).convertToDomain();
    }

    @Override
    public Resume update(Resume resume) {
        Long resumeId = repository.update(resume);
        if (resumeId < 0) {
            throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_RESUME);
        }

        return resumeReadRepository.getReferenceById(resumeId);
    }

}
