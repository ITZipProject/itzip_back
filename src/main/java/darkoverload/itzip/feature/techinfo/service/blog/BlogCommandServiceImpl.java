package darkoverload.itzip.feature.techinfo.service.blog;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.request.BlogUpdateIntroRequest;
import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import darkoverload.itzip.feature.techinfo.service.blog.port.BlogCommandRepository;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 블로그 명령(생성, 수정) 관련 서비스 구현 클래스.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BlogCommandServiceImpl implements BlogCommandService {

    private final BlogCommandRepository blogCommandRepository;
    private final UserRepository userRepository;

    /**
     * 새로운 블로그를 생성합니다.
     *
     * @param user 블로그를 생성할 사용자
     */
    @Override
    public void create(User user) {
        blogCommandRepository.save(Blog.from(user));
    }

    /**
     * 블로그 소개글을 업데이트합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @param request     블로그 소개글 업데이트 요청
     * @throws RestApiException 사용자를 찾을 수 없을 때 발생
     */
    @Override
    public void update(CustomUserDetails userDetails, BlogUpdateIntroRequest request) {
        Long userId = userRepository.findByEmail(userDetails.getEmail())
                .map(UserEntity::convertToDomain)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                )
                .getId();

        blogCommandRepository.update(userId, request.intro());
    }

    /**
     * 블로그의 공개 상태를 업데이트합니다.
     *
     * @param blogId 블로그 ID
     * @param status 새로운 공개 상태
     */
    @Override
    public void updateStatus(Long blogId, boolean status) {
        blogCommandRepository.update(blogId, status);
    }

}
