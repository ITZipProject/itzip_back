package darkoverload.itzip.feature.techinfo.service.blog.port;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;

import java.util.Optional;

public interface BlogReadRepository {

    Optional<Blog> findByBlogId(Long id);

    Optional<Blog> findByUserId(Long id);

    Optional<Blog> findByNickname(String nickname);

    Blog getById(Long id);

    Blog getByUserId(Long id);

    Blog getByNickname(String nickname);

}
