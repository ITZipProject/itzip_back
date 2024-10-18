package darkoverload.itzip.feature.resume.repository.qualification;


import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import darkoverload.itzip.feature.resume.service.resume.port.QualificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class QualificationRepositoryImpl implements QualificationRepository {

    private final QualificationJpaRepository qualificationRepository;

    @Override
    public List<Qualification> findByAllResumeId(Long resumeId) {
        return qualificationRepository.findByAllResumeId(resumeId).stream().map(QualificationEntity::convertToDomain).toList();
    }

    @Override
    public List<Qualification> update(List<Qualification> qualifications, Resume resume) {
        List<Long> deleteQualifications = getQualificationDeleteIds(qualifications, resume);
        deleteAllById(deleteQualifications);

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
    public List<Qualification> saveAll(List<Qualification> qualifications) {
        List<QualificationEntity> qualificationEntities = qualifications.stream().map(Qualification::toEntity).toList();
        return qualificationRepository.saveAll(qualificationEntities).stream().map(QualificationEntity::convertToDomain).toList();
    }

    private List<Long> getQualificationDeleteIds(List<Qualification> qualifications, Resume resume) {
        List<Long> qualificationIds = getQualificationIds(resume.getResumeId());
        List<Long> updateIds = getQualificationUpdateIds(qualifications);

        return qualificationIds.stream().filter(id -> !updateIds.contains(id)).toList();
    }

    private List<Long> getQualificationUpdateIds(List<Qualification> qualifications) {
        return qualifications.stream().filter(Objects::nonNull).map(Qualification::getQualificationId).toList();
    }

    private List<Long> getQualificationIds(Long resumeId) {
        return findByAllResumeId(resumeId).stream().map(Qualification::getQualificationId).toList();
    }

}
