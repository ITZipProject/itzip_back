package darkoverload.itzip.feature.techinfo.service.blog.query;

import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogSummaryResponse;
import darkoverload.itzip.feature.techinfo.domain.Blog;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BlogReadSummaryServiceImpl implements BlogReadSummaryService {

    // 블로그 정보를 조회하는 서비스
    private final BlogReadService blogReadService;

    public BlogReadSummaryServiceImpl(
            @Qualifier("blogReadServiceImpl") BlogReadService blogReadService
    ) {
        this.blogReadService = blogReadService;
    }

    @Override
    public BlogSummaryResponse getBlogSummaryById(Long blogId) {
        // 블로그 ID를 기반으로 블로그를 조회
        Blog blog = blogReadService.getBlogById(blogId);

        // 블로그 객체를 요약 정보로 변환하여 반환
        return blog.convertToBlogBasicInfoResponse();
    }
}