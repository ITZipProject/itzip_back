package darkoverload.itzip.feature.resume.repository.activity;

import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityCommandJpaRepository extends JpaRepository<ActivityEntity, Long>  {

}
