package darkoverload.itzip.feature.resume.repository.qualification;


import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QualificationCommandRepositoryImpl implements QualificationCommandRepository {

    private final QualificationCommandJpaRepository qualificationRepository;

    @Override
    public List<Qualification> update(List<Qualification> qualifications) {
        return saveAll(qualifications);
    }

    @Override
    public Qualification save(Qualification qualification) {
        return qualificationRepository.save(qualification.toEntity()).convertToDomain();
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        qualificationRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAllQualifications(List<Qualification> deleteQualifications) {
        qualificationRepository.deleteAll(deleteQualifications.stream().map(Qualification::toEntity).toList());
    }

    @Override
    public List<Qualification> saveAll(List<Qualification> qualifications) {
        List<QualificationEntity> qualificationEntities = qualifications.stream().map(Qualification::toEntity).toList();
        return qualificationRepository.saveAll(qualificationEntities).stream().map(QualificationEntity::convertToDomain).toList();
    }

}
