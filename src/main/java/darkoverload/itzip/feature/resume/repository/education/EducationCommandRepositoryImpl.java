package darkoverload.itzip.feature.resume.repository.education;


import darkoverload.itzip.feature.resume.domain.education.Education;
import darkoverload.itzip.feature.resume.entity.EducationEntity;
import darkoverload.itzip.feature.resume.service.resume.port.education.EducationCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EducationCommandRepositoryImpl implements EducationCommandRepository {

    private final EducationCommandJpaRepository repository;

    @Override
    public List<Education> update(List<Education> educations){
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

    @Override
    public void deleteAllEducations(List<Education> deleteEducations) {
        repository.deleteAll(deleteEducations.stream().map(Education::toEntity).toList());
    }

}
