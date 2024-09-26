package darkoverload.itzip.feature.resume.repository.activity;

import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import darkoverload.itzip.feature.resume.repository.activity.Custom.CustomActivityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAActivityRepository extends JpaRepository<ActivityEntity, Long> , CustomActivityRepository {

}
