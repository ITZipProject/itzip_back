package darkoverload.itzip.feature.techinfo.service.blog;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import darkoverload.itzip.feature.techinfo.domain.blog.BlogDetails;
import darkoverload.itzip.feature.techinfo.domain.blog.BlogPostTimeline;
import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import darkoverload.itzip.feature.techinfo.service.blog.port.BlogReadRepository;
import darkoverload.itzip.feature.techinfo.service.post.PostReadService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 블로그 조회 관련 서비스 구현 클래스.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogReadServiceImpl implements BlogReadService {

    private static final int LIMIT = 4;

    private final BlogReadRepository blogReadRepository;
    private final PostReadService postReadService;

    /**
     * 블로그 ID로 블로그를 조회합니다.
     *
     * @param id 블로그 ID
     * @return Optional<Blog>
     */
    @Override
    public Optional<Blog> findById(Long id) {
        return blogReadRepository.findByBlogId(id);
    }

    /**
     * 사용자 ID로 블로그를 조회합니다.
     *
     * @param id 사용자 ID
     * @return Optional<Blog>
     */
    @Override
    public Optional<Blog> findByUserId(Long id) {
        return blogReadRepository.findByUserId(id);
    }

    /**
     * 사용자 닉네임으로 블로그를 조회합니다.
     *
     * @param nickname 사용자 닉네임
     * @return Optional<Blog>
     */
    @Override
    public Optional<Blog> findByNickname(String nickname) {
        return blogReadRepository.findByNickname(nickname);
    }

    /**
     * 블로그 ID로 블로그를 조회하고, 없으면 예외를 발생시킵니다.
     *
     * @param id 블로그 ID
     * @return Blog
     * @throws RestApiException 블로그를 찾을 수 없을 때 발생
     */
    @Override
    public Blog getById(Long id) {
        return this.findById(id).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG)
        );
    }

    /**
     * 사용자 닉네임으로 블로그 상세 정보를 조회합니다.
     *
     * @param nickname 사용자 닉네임
     * @return 블로그 상세 정보
     * @throws RestApiException 블로그를 찾을 수 없을 때 발생
     */
    @Override
    public BlogDetails getBlogDetailByNickname(String nickname) {
        Blog blog = this.findByNickname(nickname).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG)
        );

        List<YearlyPostStats> yearlyPostCounts = postReadService.getYearlyPostStatsByBlogId(blog.getId());

        return BlogDetails.from(blog, yearlyPostCounts);
    }

    /**
     * 블로그 ID와 생성 날짜를 기준으로 최근 포스트 타임라인을 조회합니다.
     *
     * @param blogId     블로그 ID
     * @param createDate 기준 날짜
     * @return 블로그 게시글 타임라인
     * @throws RestApiException 블로그를 찾을 수 없을 때 발생
     */
    @Override
    public BlogPostTimeline getBlogRecentPostsByIdAndCreateDate(Long blogId, LocalDateTime createDate) {
        String nickname = this.findById(blogId)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG)
                )
                .getUser()
                .getNickname();

        List<Post> posts = postReadService.getPostsByDateRange(blogId, createDate, LIMIT);

        return BlogPostTimeline.from(nickname, posts);
    }

}
