package darkoverload.itzip.feature.techinfo.infrastructure.persistence;

import darkoverload.itzip.feature.techinfo.domain.entity.Scrap;
import darkoverload.itzip.feature.techinfo.domain.repository.ScrapRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapJpaRepository extends JpaRepository<Scrap, Long>, ScrapRepository {
}
