package darkoverload.itzip.feature.resume.repository.career;


import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.CareerEntity;
import darkoverload.itzip.feature.resume.service.resume.port.CareerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CareerRepositoryImpl implements CareerRepository {

    private final CareerJpaRepository repository;

    @Override
    public List<Career> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(CareerEntity::convertToDomain).toList();
    }

    @Override
    public Career save(Career career) {
        return repository.save(career.toEntity()).convertToDomain();
    }

    @Override
    public List<Career> update(List<Career> careers) {
        return saveAll(careers);
    }

    @Override
    public List<Career> saveAll(List<Career> careers) {
        List<CareerEntity> careerEntities = careers.stream().map(Career::toEntity).toList();
        return repository.saveAll(careerEntities).stream().map(CareerEntity::convertToDomain).toList();
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAllCareers(List<Career> deleteCareers) {
        repository.deleteAll(deleteCareers.stream()
                .map(Career::toEntity)
                .toList());
    }

    private List<Long> getDeleteCareerIds(List<Career> careers, Resume resume) {
        List<Long> careerIds = getCareerIds(resume.getResumeId());

        List<Long> updateIds = getUpdateCareerIds(careers);

        return careerIds.stream()
                .filter(id -> !updateIds.contains(id)).toList();
    }

    private List<Long> getUpdateCareerIds(List<Career> careers) {
        return careers.stream().filter(Objects::nonNull).map(Career::getCareerId).toList();
    }

    private List<Long> getCareerIds(Long resumeId) {
        return findAllByResumeId(resumeId).stream().map(Career::getCareerId).toList();
    }


}
