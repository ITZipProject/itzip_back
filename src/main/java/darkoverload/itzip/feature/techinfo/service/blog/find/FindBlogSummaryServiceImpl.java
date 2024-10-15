package darkoverload.itzip.feature.techinfo.service.blog.find;

import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogSummaryResponse;
import darkoverload.itzip.feature.techinfo.domain.Blog;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FindBlogSummaryServiceImpl implements FindBlogSummaryService {

    // 블로그 정보를 조회하는 서비스
    private final FindBlogSearchService findBlogSearchService;

    public FindBlogSummaryServiceImpl(
            @Qualifier("findBlogSearchServiceImpl") FindBlogSearchService findBlogSearchService
    ) {
        this.findBlogSearchService = findBlogSearchService;
    }

    @Override
    public BlogSummaryResponse findBlogSummaryById(Long blogId) {
        // 블로그 ID를 기반으로 블로그를 조회
        Blog blog = findBlogSearchService.findBlogSearchById(blogId);

        // 블로그 객체를 요약 정보로 변환하여 반환
        return blog.convertToBlogBasicInfoResponse();
    }
}