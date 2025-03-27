package darkoverload.itzip.feature.techinfo.application.service.query.impl;

import darkoverload.itzip.feature.techinfo.ui.payload.response.BlogResponse;
import darkoverload.itzip.feature.techinfo.application.service.query.BlogQueryService;
import darkoverload.itzip.feature.techinfo.domain.entity.Blog;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.fixture.BlogFixture;
import darkoverload.itzip.global.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SqlGroup({
        @Sql(scripts = "/sql/techinfo/default-insert-blog-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/techinfo/default-delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@SpringBootTest
@ActiveProfiles("test")
class BlogQueryServiceImplTest {

    @Autowired
    private BlogQueryService blogQueryService;

    @Test
    @DisplayName("블로그 ID로 블로그 정보를 반환한다.")
    void getBlogResponseById() {
        // When
        final BlogResponse result = blogQueryService.getBlogResponseById(BlogFixture.DEFAULT_ID);

        // Then
        assertAll(
                () -> assertThat(result.id()).isEqualTo(BlogFixture.DEFAULT_ID),
                () -> assertThat(result.email()).isEqualTo(UserFixture.DEFAULT_EMAIL),
                () -> assertThat(result.nickname()).isEqualTo(UserFixture.DEFAULT_NICKNAME),
                () -> assertThat(result.profileImageUri()).isEqualTo(UserFixture.DEFAULT_PROFILE_IMAGE_URI),
                () -> assertThat(result.intro()).isEqualTo(BlogFixture.DEFAULT_INTRO)
        );
    }

    @Test
    @DisplayName("닉네임으로 블로그 정보를 반환한다.")
    void getBlogResponseByUserNickname() {
        // When
        final BlogResponse result = blogQueryService.getBlogResponseByUserNickname(UserFixture.DEFAULT_NICKNAME);

        // Then
        assertAll(
                () -> assertThat(result.id()).isEqualTo(BlogFixture.DEFAULT_ID),
                () -> assertThat(result.email()).isEqualTo(UserFixture.DEFAULT_EMAIL),
                () -> assertThat(result.nickname()).isEqualTo(UserFixture.DEFAULT_NICKNAME),
                () -> assertThat(result.profileImageUri()).isEqualTo(UserFixture.DEFAULT_PROFILE_IMAGE_URI),
                () -> assertThat(result.intro()).isEqualTo(BlogFixture.DEFAULT_INTRO)
        );
    }

    @Test
    @DisplayName("블로그 ID로 블로그를 조회한다.")
    void getBlogById() {
        // When
        final Blog result = blogQueryService.getBlogById(BlogFixture.DEFAULT_ID);

        // Then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(BlogFixture.DEFAULT_ID),
                () -> assertThat(result.getUser().getId()).isEqualTo(UserFixture.DEFAULT_ID),
                () -> assertThat(result.getUser().getEmail()).isEqualTo(UserFixture.DEFAULT_EMAIL),
                () -> assertThat(result.getUser().getNickname()).isEqualTo(UserFixture.DEFAULT_NICKNAME),
                () -> assertThat(result.getUser().getPassword()).isEqualTo(UserFixture.DEFAULT_PASSWORD),
                () -> assertThat(result.getUser().getImageUrl()).isEqualTo(UserFixture.DEFAULT_PROFILE_IMAGE_URI),
                () -> assertThat(result.getUser().getAuthority()).isEqualTo(UserFixture.DEFAULT_AUTHORITY),
                () -> assertThat(result.getUser().getSnsType()).isEqualTo(UserFixture.DEFAULT_SNS_TYPE),
                () -> assertThat(result.getIntro()).isEqualTo(BlogFixture.DEFAULT_INTRO),
                () -> assertThat(result.getCreatedAt()).isEqualTo(BlogFixture.DEFAULT_DATE_TIME),
                () -> assertThat(result.getUpdatedAt()).isEqualTo(BlogFixture.DEFAULT_DATE_TIME)
        );
    }

    @Test
    @DisplayName("닉네임으로 블로그 ID를 조회한다.")
    void getBlogIdByUserNickname() {
        // When
        final Long result = blogQueryService.getBlogIdByUserNickname(UserFixture.DEFAULT_NICKNAME);

        // Then
        assertThat(result).isEqualTo(BlogFixture.DEFAULT_ID);
    }

    @Test
    @DisplayName("여러 블로그 ID로 블로그 맵을 조회한다.")
    void getBlogMapByIds() {
        // Given
        final Set<Long> blogIds = Set.of(BlogFixture.DEFAULT_ID);

        // When
        final Map<Long, Blog> blogMap = blogQueryService.getBlogMapByIds(blogIds);

        // Then
        assertThat(blogMap).containsKey(BlogFixture.DEFAULT_ID);
    }

    @Test
    @DisplayName("존재하지 않는 블로그 ID 조회 시 예외가 발생한다.")
    void getBlogResponseByIdWithNonExistentBlogId() {
        // Given
        final Long nonExistentBlogId = -1L;

        // When & Then
        assertThatThrownBy(() -> blogQueryService.getBlogResponseById(nonExistentBlogId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.BLOG_NOT_FOUND);
    }

}
