package darkoverload.itzip.feature.resume.repository.education;


import darkoverload.itzip.feature.resume.domain.education.Education;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.EducationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class EducationRepository {

    private final JPAEducationRepository repository;


    public List<Education> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(EducationEntity::convertToDomain).toList();
    }

    public List<Education> update(List<Education> educations, Resume resume){
        List<Long> educationIds = findAllByResumeId(resume.getResumeId()).stream().map(Education::getEducationId).toList();

        List<Long> updateIds = educations.stream().filter(Objects::nonNull).map(Education::getEducationId).toList();

        List<Long> deleteEducations = educationIds.stream()
                .filter(id-> !updateIds.contains(id)).toList();

        if(!deleteEducations.isEmpty()) repository.deleteAllById(deleteEducations);

        return saveAll(educations.stream().map(Education::toEntity).toList());
    }

    public List<Education> saveAll(List<EducationEntity> educationEntities) {
        return repository.saveAll(educationEntities).stream().map(EducationEntity::convertToDomain).toList();
    }
}
