package darkoverload.itzip.feature.techinfo.service.blog.update;

import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.repository.blog.BlogRepository;

import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateBlogDisableServiceImpl implements UpdateBlogDisableService {

    // 블로그 정보를 저장하고 관리하는 리포지토리
    private final BlogRepository blogRepository;

    // 블로그 정보를 조회하는 서비스
    private final FindBlogService findBlogService;

    public UpdateBlogDisableServiceImpl(
            BlogRepository blogRepository,
            @Qualifier("findBlogServiceImpl") FindBlogService findBlogService
    ) {
        this.blogRepository = blogRepository;
        this.findBlogService = findBlogService;
    }

    @Transactional
    @Override
    public void updateBlogDisable(Long blogId) {
        // 공통 BlogFinderService를 사용하여 블로그를 조회
        Blog blog = findBlogService.findBlogById(blogId);

        // 블로그를 비공개 상태로 설정
        blog.setIsPublic(false);

        // 변경된 블로그 정보를 DB에 저장
        blogRepository.save(blog.convertToEntity());
    }
}