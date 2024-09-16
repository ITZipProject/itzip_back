package darkoverload.itzip.feature.algorithm.repository.mapping;

import darkoverload.itzip.feature.algorithm.entity.ProblemTagMappingEntity;
import darkoverload.itzip.feature.algorithm.entity.embedded.ProblemTagMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemTagMappingRepository extends JpaRepository<ProblemTagMappingEntity, ProblemTagMappingId> {
}
