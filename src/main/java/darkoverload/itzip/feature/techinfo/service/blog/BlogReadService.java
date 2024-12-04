package darkoverload.itzip.feature.techinfo.service.blog;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import darkoverload.itzip.feature.techinfo.domain.blog.BlogDetails;
import darkoverload.itzip.feature.techinfo.domain.blog.BlogPostTimeline;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BlogReadService {

    Optional<Blog> findById(Long id);

    Optional<Blog> findByUserId(Long id);

    Optional<Blog> findByNickname(String nickname);

    Blog getById(Long id);

    BlogDetails getBlogDetailByNickname(String nickname);

    BlogPostTimeline getBlogRecentPostsByIdAndCreateDate(Long blogId, LocalDateTime createDate);

}
