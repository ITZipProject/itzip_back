package darkoverload.itzip.feature.resume.repository.activity;

import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityReadJpaRepository extends JpaRepository <ActivityEntity, Long> {
    List<ActivityEntity> findAllByResumeId(Long id);
}
