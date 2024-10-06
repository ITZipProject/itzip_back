package darkoverload.itzip.feature.techinfo.service.blog.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.request.BlogUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.response.BlogBasicInfoResponse;
import darkoverload.itzip.feature.techinfo.controller.response.BlogDetailInfoResponse;
import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.dto.year.YearlyPostDto;
import darkoverload.itzip.feature.techinfo.repository.blog.BlogRepository;
import darkoverload.itzip.feature.techinfo.service.blog.BlogService;
import darkoverload.itzip.feature.techinfo.service.post.PostService;
import darkoverload.itzip.feature.techinfo.service.shared.SharedService;
import darkoverload.itzip.feature.user.domain.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final SharedService sharedService;
    private final PostService postService;

    @Override
    @Transactional
    public void createBlog(User user) {
        Blog blog = new Blog(user); // 블로그 생성
        blogRepository.save(blog.convertToEntity());
    }

    @Override
    @Transactional
    public void updateBlog(CustomUserDetails userDetails, BlogUpdateRequest request) {
        Long userId = sharedService.getUserByEmail(userDetails.getEmail()).getId();
        Blog blog = sharedService.getBlogById(userId);

        blog.setIntro(request.getIntro()); // 블로그 소개글 업데이트
        blogRepository.save(blog.convertToEntity());
    }

    @Override
    @Transactional
    public void setBlogHiddenStatus(Long blogId) {
        Blog blog = sharedService.getBlogById(blogId);

        blog.setIsPublic(false); // 블로그 비공개 설정
        blogRepository.save(blog.convertToEntity());
    }

    @Override
    public BlogBasicInfoResponse getBasicBlogInfo(Long blogId) {
        Blog blog = sharedService.getBlogById(blogId);
        return blog.convertToBlogBasicInfoResponse(); // 블로그 기본 정보 반환
    }

    @Override
    public BlogDetailInfoResponse getDetailBlogInfo(CustomUserDetails userDetails, Optional<String> nickname) {
        Long userId = nickname
                .map(sharedService::getUserByNickname) // 닉네임으로 사용자 조회
                .orElseGet(() -> sharedService.getUserByEmail(userDetails.getEmail()))  // 이메일로 사용자 조회
                .getId(); // 사용자 ID 추출

        Blog blog = sharedService.getBlogById(userId);
        List<YearlyPostDto> yearlyPostCounts = postService.getYearlyPostCounts(blog.getId()); // 연간 포스트 수 조회

        return blog.convertToBlogDetailInfoResponse(yearlyPostCounts); // 블로그 상세 정보 반환
    }
}