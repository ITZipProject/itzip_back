package darkoverload.itzip.feature.techinfo.service.blog.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.request.BlogUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogBasicInfoResponse;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogDetailInfoResponse;
import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.dto.post.year.YearlyPostDto;
import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.techinfo.repository.blog.BlogRepository;
import darkoverload.itzip.feature.techinfo.repository.post.PostRepository;
import darkoverload.itzip.feature.techinfo.service.blog.BlogService;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void createBlog(User user) {
        Blog blog = new Blog(user); // 블로그 생성
        blogRepository.save(blog.convertToEntity());
    }

    @Override
    @Transactional
    public void updateBlog(CustomUserDetails userDetails, BlogUpdateRequest request) {
        Long userId = userRepository.findByEmail(userDetails.getEmail())
                .map(UserEntity::convertToDomain)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                )
                .getId();

        Blog blog = getBlogById(userId);
        blog.setIntro(request.getIntro()); // 블로그 소개글 업데이트
        blogRepository.save(blog.convertToEntity());
    }

    @Override
    @Transactional
    public void setBlogHiddenStatus(Long blogId) {
        Blog blog = getBlogById(blogId);

        blog.setIsPublic(false); // 블로그 비공개 설정
        blogRepository.save(blog.convertToEntity());
    }

    @Override
    @Transactional(readOnly = true)
    public Blog getBlogById(Long id) {
        return blogRepository.findByIdAndIsPublic(id)
                .map(BlogEntity::convertToDomain)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG)
                );
    }

    @Override
    public BlogBasicInfoResponse getBasicBlogInfo(Long blogId) {
        Blog blog = getBlogById(blogId);
        return blog.convertToBlogBasicInfoResponse(); // 블로그 기본 정보 반환
    }

    @Override
    @Transactional(readOnly = true)
    public BlogDetailInfoResponse getDetailBlogInfo(CustomUserDetails userDetails, String nickname) {
        User user = userRepository.findByNickname(nickname) // 닉네임으로 사용자 조회
                .map(UserEntity::convertToDomain)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                );

        if (user.getEmail().equals(userDetails.getEmail())) {
            throw new RestApiException(CommonExceptionCode.FORBIDDEN);
        }

        // 블로그 조회 및 연간 포스트 수 조회
        Blog blog = getBlogById(user.getId());
        List<YearlyPostDto> yearlyPostCounts = getYearlyPostCounts(blog.getId());

        return blog.convertToBlogDetailInfoResponse(yearlyPostCounts); // 블로그 상세 정보 반환
    }

    private List<YearlyPostDto> getYearlyPostCounts(Long blogId) {
        return postRepository.findYearlyPostCounts(blogId); // 연도별 포스트 통계 조회
    }
}