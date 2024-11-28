package darkoverload.itzip.feature.resume.repository.career;

import darkoverload.itzip.feature.resume.entity.CareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerReadJpaRepository extends JpaRepository<CareerEntity, Long> {
    List<CareerEntity> findAllByResumeId(Long id);

}
