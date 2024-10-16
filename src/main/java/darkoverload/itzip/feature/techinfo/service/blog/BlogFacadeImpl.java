package darkoverload.itzip.feature.techinfo.service.blog;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.request.UpdateBlogIntroRequest;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogDetailsResponse;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogRecentPostsResponse;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogSummaryResponse;
import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.service.blog.create.CreateBlogService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogDetailsService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogRecentPostsService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogSummaryService;
import darkoverload.itzip.feature.techinfo.service.blog.update.UpdateBlogDisableService;
import darkoverload.itzip.feature.techinfo.service.blog.update.UpdateBlogIntroService;
import darkoverload.itzip.feature.user.domain.User;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlogFacadeImpl implements BlogFacade {

    @Qualifier("createBlogServiceImpl")
    private final CreateBlogService createBlogService;

    @Qualifier("updateBlogIntroServiceImpl")
    private final UpdateBlogIntroService updateBlogIntroService;

    @Qualifier("updateBlogDisableServiceImpl")
    private final UpdateBlogDisableService updateBlogDisableService;

    @Qualifier("findBlogServiceImpl")
    private final FindBlogService findBlogService;

    @Qualifier("findBlogSummaryServiceImpl")
    private final FindBlogSummaryService findBlogSummaryService;

    @Qualifier("findBlogDetailsServiceImpl")
    private final FindBlogDetailsService findBlogDetailsService;

    @Qualifier("findBlogRecentPostsServiceImpl")
    private final FindBlogRecentPostsService findBlogRecentPostsService;

    @Override
    public void createBlog(User user) {
        createBlogService.createBlog(user);
    }

    @Override
    public void updateBlogIntro(CustomUserDetails userDetails, UpdateBlogIntroRequest request) {
        updateBlogIntroService.updateBlogIntro(userDetails, request);
    }

    @Override
    public void updateBlogDisable(Long blogId) {
        updateBlogDisableService.updateBlogDisable(blogId);
    }

    @Override
    public Blog findBlogById(Long id) {
        return findBlogService.findBlogById(id);
    }

    @Override
    public BlogSummaryResponse findBlogSummaryById(Long blogId) {
        return findBlogSummaryService.findBlogSummaryById(blogId);
    }

    @Override
    public BlogDetailsResponse findBlogDetailsByNickname(CustomUserDetails userDetails, String nickname) {
        return findBlogDetailsService.findBlogDetailsByNickname(userDetails, nickname);
    }

    @Override
    public BlogRecentPostsResponse findBlogRecentPostsByBlogIdAndCreateDate(Long blogId, LocalDateTime createDate) {
        return findBlogRecentPostsService.findBlogRecentPostsByBlogIdAndCreateDate(blogId, createDate);
    }
}