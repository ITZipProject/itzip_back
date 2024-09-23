package darkoverload.itzip.feature.resume.repository.career;


import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.CareerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CareerRepository {

    private final JPACareerRepository repository;


    public List<Career> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(CareerEntity::convertToDomain).toList();
    }

    public List<Career> update(List<Career> careers, Resume resume){
        List<Long> careerIds = findAllByResumeId(resume.getResumeId()).stream().map(Career::getCareerId).toList();

        List<Long> updateIds = careers.stream().filter(Objects::nonNull).map(Career::getCareerId).toList();

        List<Long> deleteCareers = careerIds.stream()
                .filter(id -> !updateIds.contains(id)).toList();

        if(!deleteCareers.isEmpty()) repository.deleteAllById(deleteCareers);

        return saveAll(careers.stream().map(Career::toEntity).toList());
    }

    public List<Career> saveAll(List<CareerEntity> careerEntities) {
        return repository.saveAll(careerEntities).stream().map(CareerEntity::convertToDomain).toList();
    }
}
