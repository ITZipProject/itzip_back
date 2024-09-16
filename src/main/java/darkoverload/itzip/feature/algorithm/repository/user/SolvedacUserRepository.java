package darkoverload.itzip.feature.algorithm.repository.user;

import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedacUserRepository extends JpaRepository<SolvedacUserEntity, Long> {
}
