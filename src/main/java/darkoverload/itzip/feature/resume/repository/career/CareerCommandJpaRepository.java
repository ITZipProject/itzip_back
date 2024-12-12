package darkoverload.itzip.feature.resume.repository.career;

import darkoverload.itzip.feature.resume.entity.CareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerCommandJpaRepository extends JpaRepository<CareerEntity, Long> {

}
