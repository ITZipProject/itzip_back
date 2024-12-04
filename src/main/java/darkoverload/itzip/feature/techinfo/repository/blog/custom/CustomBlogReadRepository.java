package darkoverload.itzip.feature.techinfo.repository.blog.custom;

import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import java.util.Optional;

public interface CustomBlogReadRepository {

    Optional<BlogEntity> findByBlogId(Long id);

    Optional<BlogEntity> findByUserId(Long id);

    Optional<BlogEntity> findByNickname(String nickname);

}
