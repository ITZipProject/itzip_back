package darkoverload.itzip.feature.resume.repository.resume;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import darkoverload.itzip.feature.resume.service.resume.port.resume.ResumeReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ResumeReadRepositoryImpl implements ResumeReadRepository{

    private final ResumeReadJpaRepository repository;

    public List<Resume> searchResumeInfos(String search, Pageable pageable) {
        List<ResumeEntity> resumeEntities = repository.searchResumeInfos(search, pageable);

        return resumeEntities.stream().map(ResumeEntity::convertToDomain).collect(Collectors.toList());
    }

    @Override
    public Resume getReferenceById(Long id) {
        return repository.getReferenceById(id).convertToDomain();
    }

}
