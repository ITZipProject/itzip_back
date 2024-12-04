package darkoverload.itzip.feature.techinfo.repository.blog;

import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.techinfo.repository.blog.custom.CustomBlogReadRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBlogReadRepository extends JpaRepository<BlogEntity, Long>, CustomBlogReadRepository {
}
