package darkoverload.itzip.feature.resume.repository.qualification;


import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class QualificationRepository {

    private final JPAQualificationRepository qualificationRepository;

    public List<Qualification> findByAllResumeId(Long resumeId) {
        return qualificationRepository.findByAllResumeId(resumeId).stream().map(QualificationEntity::convertToDomain).toList();
    }

    public List<Qualification> update(List<Qualification> qualifications, Resume resume) {
        List<Long> qualificationIds = findByAllResumeId(resume.getResumeId()).stream().map(Qualification::getQualificationId).toList();

        List<Long> updateIds = qualifications.stream().filter(Objects::nonNull).map(Qualification::getQualificationId).toList();

        List<Long> deleteQualifications = qualificationIds.stream().filter(id-> !updateIds.contains(id)).toList();

        qualificationRepository.deleteAllById(deleteQualifications);

        return saveAll(qualifications.stream().map(Qualification::toEntity).toList());
    }


    public List<Qualification> saveAll(List<QualificationEntity> qualificationEntities) {
        return qualificationRepository.saveAll(qualificationEntities).stream().map(QualificationEntity::convertToDomain).toList();
    }
}
