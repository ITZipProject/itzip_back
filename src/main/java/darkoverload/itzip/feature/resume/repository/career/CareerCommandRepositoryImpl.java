package darkoverload.itzip.feature.resume.repository.career;

import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.entity.CareerEntity;
import darkoverload.itzip.feature.resume.service.resume.port.career.CareerCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CareerCommandRepositoryImpl implements CareerCommandRepository {

    private final CareerCommandJpaRepository repository;

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

}
