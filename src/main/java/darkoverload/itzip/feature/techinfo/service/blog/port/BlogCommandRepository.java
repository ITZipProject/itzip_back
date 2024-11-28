package darkoverload.itzip.feature.techinfo.service.blog.port;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;

public interface BlogCommandRepository {

    void save(Blog blog);

    void update(Long userId, String newIntro);

    void update(Long blogId, boolean status);

}
