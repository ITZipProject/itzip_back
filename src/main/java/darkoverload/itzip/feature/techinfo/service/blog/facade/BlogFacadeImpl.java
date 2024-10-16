package darkoverload.itzip.feature.techinfo.service.blog.facade;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.request.UpdateBlogIntroRequest;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogDetailsResponse;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogRecentPostsResponse;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogSummaryResponse;
import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.service.blog.command.BlogCreateService;
import darkoverload.itzip.feature.techinfo.service.blog.query.BlogReadDetailsService;
import darkoverload.itzip.feature.techinfo.service.blog.query.BlogReadRecentPostsService;
import darkoverload.itzip.feature.techinfo.service.blog.query.BlogReadService;
import darkoverload.itzip.feature.techinfo.service.blog.query.BlogReadSummaryService;
import darkoverload.itzip.feature.techinfo.service.blog.command.BlogDisableService;
import darkoverload.itzip.feature.techinfo.service.blog.command.BlogUpdateIntroService;
import darkoverload.itzip.feature.user.domain.User;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlogFacadeImpl implements BlogFacade {

    @Qualifier("blogCreateServiceImpl")
    private final BlogCreateService blogCreateService;

    @Qualifier("blogUpdateIntroServiceImpl")
    private final BlogUpdateIntroService blogUpdateIntroService;

    @Qualifier("blogDisableServiceImpl")
    private final BlogDisableService blogDisableService;

    @Qualifier("blogReadServiceImpl")
    private final BlogReadService blogReadService;

    @Qualifier("blogReadSummaryServiceImpl")
    private final BlogReadSummaryService blogReadSummaryService;

    @Qualifier("blogReadDetailsServiceImpl")
    private final BlogReadDetailsService blogReadDetailsService;

    @Qualifier("blogReadRecentPostsServiceImpl")
    private final BlogReadRecentPostsService blogReadRecentPostsService;

    @Override
    public void createBlog(User user) {
        blogCreateService.createBlog(user);
    }

    @Override
    public void updateBlogIntro(CustomUserDetails userDetails, UpdateBlogIntroRequest request) {
        blogUpdateIntroService.updateBlogIntro(userDetails, request);
    }

    @Override
    public void disableBlog(Long blogId) {
        blogDisableService.disableBlog(blogId);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogReadService.getBlogById(id);
    }

    @Override
    public BlogSummaryResponse getBlogSummaryById(Long blogId) {
        return blogReadSummaryService.getBlogSummaryById(blogId);
    }

    @Override
    public BlogDetailsResponse getBlogDetailsByNickname(CustomUserDetails userDetails, String nickname) {
        return blogReadDetailsService.getBlogDetailsByNickname(userDetails, nickname);
    }

    @Override
    public BlogRecentPostsResponse getBlogRecentPostsByBlogIdAndCreateDate(Long blogId, LocalDateTime createDate) {
        return blogReadRecentPostsService.getBlogRecentPostsByBlogIdAndCreateDate(blogId, createDate);
    }
}