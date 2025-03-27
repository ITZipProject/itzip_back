package darkoverload.itzip.feature.techinfo.infrastructure.persistence;

import darkoverload.itzip.feature.techinfo.domain.repository.LikeRepository;
import darkoverload.itzip.feature.techinfo.domain.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<Like, Long>, LikeRepository {
}
