package darkoverload.itzip.feature.techinfo.service.blog.port;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;

public interface BlogCommandRepository {

    Blog save(Blog blog);

    Blog update(Long userId, String newIntro);

    Blog update(Long blogId, boolean status);

}
