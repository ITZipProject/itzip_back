package darkoverload.itzip.feature.techinfo.service.blog.find;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogDetailsResponse;
import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.dto.post.year.YearlyPostDto;
import darkoverload.itzip.feature.techinfo.repository.post.PostRepository;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindBlogDetailsServiceImpl implements FindBlogDetailsService {

    // 유저 정보를 다루는 리포지토리
    private final UserRepository userRepository;

    // 포스트 정보를 다루는 리포지토리
    private final PostRepository postRepository;

    // 블로그 정보를 조회하는 서비스
    private final FindBlogService findBlogService;

    public FindBlogDetailsServiceImpl(
            UserRepository userRepository,
            PostRepository postRepository,
            @Qualifier("findBlogServiceImpl") FindBlogService findBlogService
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.findBlogService = findBlogService;
    }

    /**
     * 주어진 닉네임을 사용하여 블로그의 세부 정보를 조회한다.
     * 유저의 인증 정보와 닉네임을 사용해 블로그를 찾고, 연도별 게시글 수를 포함한
     * 블로그 세부 정보를 반환한다.
     *
     * @param userDetails 요청한 사용자의 인증 정보.
     * @param nickname    조회할 블로그 소유자의 닉네임.
     * @return 조회된 블로그의 세부 정보.
     */
    @Transactional(readOnly = true)
    public BlogDetailsResponse findBlogDetailsByNickname(CustomUserDetails userDetails, String nickname) {
        // 닉네임을 기반으로 유저 조회, 없을 경우 예외 처리
        User user = userRepository.findByNickname(nickname)
                .map(UserEntity::convertToDomain)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER) // 유저를 찾지 못하면 예외 발생
                );

        // 요청한 유저와 블로그 소유자의 이메일이 다를 경우 예외 처리
        if (!user.getEmail().equals(userDetails.getEmail())) {
            throw new RestApiException(CommonExceptionCode.FORBIDDEN); // 접근 권한이 없을 경우 예외 발생
        }

        // 유저 ID로 블로그 조회
        Blog blog = findBlogService.findBlogById(user.getId());

        // 연도별 게시글 수 조회
        List<YearlyPostDto> yearlyPostCounts = findYearlyPostCountsByBlogId(blog.getId());

        // 블로그 세부 정보 반환
        return blog.convertToBlogDetailInfoResponse(yearlyPostCounts);
    }

    /**
     * 블로그 ID를 기반으로 연도별 게시글 수를 조회한다.
     *
     * @param blogId 조회할 블로그의 ID.
     * @return 연도별 게시글 수 목록.
     */
    private List<YearlyPostDto> findYearlyPostCountsByBlogId(Long blogId) {
        return postRepository.findYearlyPostCounts(blogId);
    }
}