package darkoverload.itzip.feature.resume.repository.education;


import darkoverload.itzip.feature.resume.domain.education.Education;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.EducationEntity;
import darkoverload.itzip.feature.resume.service.resume.port.EducationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EducationRepositoryImpl implements EducationRepository {

    private final EducationJpaRepository repository;

    @Override
    public List<Education> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(EducationEntity::convertToDomain).toList();
    }

    @Override
    public List<Education> update(List<Education> educations, Resume resume){
        List<Long> deleteEducations = getDeleteEducationIds(educations, resume);
        if(!deleteEducations.isEmpty()) {
            deleteAllById(deleteEducations);
        }

        return saveAll(educations);
    }

    @Override
    public Education save(Education education) {
        return repository.save(education.toEntity()).convertToDomain();
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public List<Education> saveAll(List<Education> educations) {
        List<EducationEntity> educationEntities = educations.stream().map(Education::toEntity).toList();
        return repository.saveAll(educationEntities).stream().map(EducationEntity::convertToDomain).toList();
    }

    private List<Long> getDeleteEducationIds(List<Education> educations, Resume resume) {
        List<Long> educationIds = getEducationIds(resume.getResumeId());
        List<Long> updateIds = getUpdateEducationIds(educations);

        return educationIds.stream()
                .filter(id-> !updateIds.contains(id)).toList();
    }

    private List<Long> getUpdateEducationIds(List<Education> educations) {
        return educations.stream().filter(Objects::nonNull).map(Education::getEducationId).toList();
    }

    private List<Long> getEducationIds(Long resumeId) {
        return findAllByResumeId(resumeId).stream().map(Education::getEducationId).toList();
    }


}
