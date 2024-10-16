package darkoverload.itzip.feature.resume.repository.career;

import darkoverload.itzip.feature.resume.entity.CareerEntity;
import darkoverload.itzip.feature.resume.repository.career.Custom.CustomCareerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerJpaRepository extends JpaRepository<CareerEntity, Long> , CustomCareerRepository {

}
