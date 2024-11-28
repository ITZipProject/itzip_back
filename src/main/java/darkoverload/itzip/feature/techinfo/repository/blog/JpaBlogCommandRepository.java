package darkoverload.itzip.feature.techinfo.repository.blog;

import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.techinfo.repository.blog.custom.CustomBlogCommandRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBlogCommandRepository extends JpaRepository<BlogEntity, Long>, CustomBlogCommandRepository {
}
