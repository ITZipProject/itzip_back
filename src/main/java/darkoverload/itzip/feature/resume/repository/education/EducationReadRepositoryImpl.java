package darkoverload.itzip.feature.resume.repository.education;

import darkoverload.itzip.feature.resume.domain.education.Education;
import darkoverload.itzip.feature.resume.entity.EducationEntity;
import darkoverload.itzip.feature.resume.service.resume.port.education.EducationReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EducationReadRepositoryImpl implements EducationReadRepository {

    private final EducationReadJpaRepository repository;

    @Override
    public List<Education> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(EducationEntity::convertToDomain).toList();
    }
}
