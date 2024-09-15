package darkoverload.itzip.feature.algorithm.repository.tag;

import darkoverload.itzip.feature.algorithm.entity.ProblemTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemTagRepository extends JpaRepository<ProblemTagEntity, Long> {
}
