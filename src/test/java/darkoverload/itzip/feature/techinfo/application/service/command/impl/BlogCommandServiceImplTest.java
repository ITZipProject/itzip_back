package darkoverload.itzip.feature.techinfo.application.service.command.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.domain.entity.Blog;
import darkoverload.itzip.feature.techinfo.domain.repository.BlogRepository;
import darkoverload.itzip.feature.techinfo.ui.payload.request.blog.BlogIntroEditRequest;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.fixture.BlogFixture;
import darkoverload.itzip.global.fixture.CustomUserDetailsFixture;
import darkoverload.itzip.global.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SqlGroup({
        @Sql(scripts = "/sql/techinfo/custom/custom-insert-blog-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/techinfo/custom/custom-delete-blog-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@SpringBootTest
@ActiveProfiles("test")
class BlogCommandServiceImplTest {

    @SpyBean
    private BlogCommandServiceImpl blogCommandService;

    @SpyBean
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Test
    @DisplayName("블로그를 생성한다.")
    void create() {
        // Given
        final Long savedUserId = UserFixture.SECOND_ID;

        // When
        blogCommandService.create(savedUserId);

        // Then
        await().atMost(5, TimeUnit.SECONDS)
                .until(() -> blogRepository.findByUserId(savedUserId).isPresent());

        final Blog result = blogRepository.findByUserId(savedUserId).get();
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(BlogFixture.SECOND_ID),
                () -> assertThat(result.getIntro()).isEqualTo(BlogFixture.DEFAULT_INTRO),
                () -> assertThat(result.getUser().getId()).isEqualTo(UserFixture.SECOND_ID),
                () -> assertThat(result.getUser().getEmail()).isEqualTo(UserFixture.SECOND_EMAIL),
                () -> assertThat(result.getUser().getNickname()).isEqualTo(UserFixture.SECOND_NICKNAME)
        );
    }

    @Test
    @DisplayName("블로그 생성 시 사용자 정보가 존재하지 않은 경우 예외 발생 시 최대 3회 재시도 후 리커버리를 호출한다.")
    void createWithNonExistentUserId() {
        // Given
        final long nonExistentId = UserFixture.NON_EXISTENT_ID;

        // When & Then
        blogCommandService.create(nonExistentId);

        // Then
        await().atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    verify(userRepository, times(3)).findById(nonExistentId);
                    verify(blogCommandService, times(1)).recoverCreate(any(RestApiException.class),eq(nonExistentId));
                });
    }

    @Test
    @DisplayName("블로그 소개글을 변경한다.")
    void updateIntro() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final BlogIntroEditRequest request = new BlogIntroEditRequest(BlogFixture.DEFAULT_NEW_INTRO);

        // When
        blogCommandService.updateIntro(userDetails, request);

        // Then
        final Blog result = blogRepository.findById(BlogFixture.DEFAULT_ID).get();
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(BlogFixture.DEFAULT_ID),
                () -> assertThat(result.getIntro()).isEqualTo(request.intro()),
                () -> assertThat(result.getUser().getEmail()).isEqualTo(userDetails.getEmail()),
                () -> assertThat(result.getUser().getPassword()).isEqualTo(userDetails.getPassword()),
                () -> assertThat(result.getUser().getNickname()).isEqualTo(userDetails.getNickname()),
                () -> assertThat(result.getUser().getAuthority()).isEqualTo(userDetails.getAuthorities().stream().findFirst().get())
        );
    }

    @Test
    @DisplayName("블로그 소개글 변경 시 사용자 정보가 존재하지 않은 경우 예외가 발생한다.")
    void updateIntroWithNonExistentUser() {
        // Given
        final CustomUserDetails userDetails = null;
        final BlogIntroEditRequest request = new BlogIntroEditRequest(BlogFixture.DEFAULT_NEW_INTRO);

        // When & Then
        assertThatThrownBy(() -> blogCommandService.updateIntro(userDetails, request))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

    @Test
    @DisplayName("블로그 소개글 변경 시 요청된 회원의 블로그 정보가 존재하지 않은 경우 예외가 발생한다.")
    void updateIntroWithNonExistentBlog() {
        // Given
        final CustomUserDetails userDetails = new CustomUserDetails(UserFixture.NON_EXISTENT_EMAIL, UserFixture.DEFAULT_PASSWORD, UserFixture.NON_EXISTENT_NICKNAME, List.of(UserFixture.DEFAULT_AUTHORITY));
        final BlogIntroEditRequest request = new BlogIntroEditRequest(BlogFixture.DEFAULT_NEW_INTRO);

        // When & Then
        assertThatThrownBy(() -> blogCommandService.updateIntro(userDetails, request))
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.BLOG_NOT_FOUND);
    }

}
