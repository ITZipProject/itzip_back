package darkoverload.itzip.feature.resume.repository.career;

import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.entity.CareerEntity;
import darkoverload.itzip.feature.resume.service.resume.port.career.CareerReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CareerReadRepositoryImpl implements CareerReadRepository {

    private final CareerReadJpaRepository repository;

    @Override
    public List<Career> findAllByResumeId(Long resumeId) {
        return repository.findAllByResumeId(resumeId).stream().map(CareerEntity::convertToDomain).toList();
    }

}
