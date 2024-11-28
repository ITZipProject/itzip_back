package darkoverload.itzip.feature.resume.repository.qualification;

import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QualificationReadRepositoryImpl implements QualificationReadRepository {

    private final QualificationReadJpaRepository repository;

    @Override
    public List<Qualification> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(QualificationEntity::convertToDomain).toList();
    }

}
