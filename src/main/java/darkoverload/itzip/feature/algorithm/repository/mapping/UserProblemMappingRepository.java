package darkoverload.itzip.feature.algorithm.repository.mapping;

import darkoverload.itzip.feature.algorithm.entity.UserProblemMappingEntity;
import darkoverload.itzip.feature.algorithm.entity.embedded.UserProblemMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProblemMappingRepository extends JpaRepository<UserProblemMappingEntity, UserProblemMappingId> {
}