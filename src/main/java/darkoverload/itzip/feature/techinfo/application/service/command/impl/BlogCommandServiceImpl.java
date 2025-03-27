package darkoverload.itzip.feature.techinfo.application.service.command.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.service.command.BlogCommandService;
import darkoverload.itzip.feature.techinfo.domain.entity.Blog;
import darkoverload.itzip.feature.techinfo.domain.repository.BlogRepository;
import darkoverload.itzip.feature.techinfo.ui.payload.request.blog.BlogIntroEditRequest;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogCommandServiceImpl implements BlogCommandService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Async
    @Retryable(
            value = RestApiException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void create(final Long userId) {
        final UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));
        final Blog blog = Blog.create(user);
        blogRepository.save(blog);
        log.debug("블로그 생성 완료: {}", blog.getId());
    }

    @Recover
    public void recoverCreate(final RestApiException e, final Long userId) {
        log.error("블로그 생성 재시도 실패: {}", userId);
    }

    @Transactional
    @Override
    public void updateIntro(final CustomUserDetails userDetails, final BlogIntroEditRequest request) {
        if (Objects.isNull(userDetails)) {
            throw new RestApiException(CommonExceptionCode.UNAUTHORIZED);
        }
        final Blog blog = blogRepository.findBlogByUser_Nickname(userDetails.getUserNickname())
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.BLOG_NOT_FOUND));
        blog.updateIntro(request.intro());
    }

}
