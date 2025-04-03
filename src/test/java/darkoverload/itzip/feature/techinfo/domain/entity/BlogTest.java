package darkoverload.itzip.feature.techinfo.domain.entity;

import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.fixture.BlogFixture;
import darkoverload.itzip.global.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class BlogTest {

    @Test
    void constructor() {
        // Given
        final long id = BlogFixture.DEFAULT_ID;
        final UserEntity user = UserFixture.getUser();
        final String intro = BlogFixture.DEFAULT_INTRO;
        final LocalDateTime createdAt = BlogFixture.DEFAULT_DATE_TIME;
        final LocalDateTime updatedAt = BlogFixture.DEFAULT_DATE_TIME;

        // When
        final Blog blog = new Blog(id, user, intro, createdAt, updatedAt);

        // Then
        assertAll(
                () -> assertThat(blog.getId()).isEqualTo(id),
                () -> assertThat(blog.getUser()).isEqualTo(user),
                () -> assertThat(blog.getIntro()).isEqualTo(intro),
                () -> assertThat(blog.getCreatedAt()).isEqualTo(createdAt),
                () -> assertThat(blog.getUpdatedAt()).isEqualTo(updatedAt)
        );
    }

    @Test
    @DisplayName("블로그를 생성한다.")
    void create() {
        // Given & When
        final Blog result = Blog.create(UserFixture.getUser());

        // Then
        assertAll(
                () -> assertThat(result.getUser()).isEqualTo(UserFixture.getUser()),
                () -> assertThat(result.getIntro()).isEqualTo(BlogFixture.DEFAULT_INTRO)
        );
    }

    @Test
    @DisplayName("블로그의 소개글을 수정한다.")
    void update() {
        // Given
        final Blog blog = BlogFixture.getBlog();

        // When
        blog.updateIntro(BlogFixture.DEFAULT_NEW_INTRO);

        // Then
        assertThat(blog.getIntro()).isEqualTo(BlogFixture.DEFAULT_NEW_INTRO);
    }

    @Test
    @DisplayName("블로그 소개글 수정 시, 소개글이 NULL 혹은 공백인 경우 예외를 발생한다.")
    void updateWithInvalidIntro() {
        // Given
        final Blog blog = BlogFixture.getBlog();

        // When & Then
        assertThatThrownBy(() -> blog.updateIntro(""))
                .isInstanceOf(RestApiException.class)
                .withFailMessage("블로그 소개글은 반드시 입력되어야 합니다.");
    }

}
