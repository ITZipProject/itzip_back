package darkoverload.itzip.feature.techinfo.infrastructure.persistence;

import darkoverload.itzip.feature.techinfo.domain.entity.Blog;
import darkoverload.itzip.feature.techinfo.domain.repository.BlogRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogJpaRepository extends JpaRepository<Blog, Long>, BlogRepository {
}
