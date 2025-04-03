package darkoverload.itzip.feature.techinfo.domain.entity;

import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.fixture.ArticleFixture;
import darkoverload.itzip.global.fixture.CommentFixture;
import darkoverload.itzip.global.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class CommentTest {

    @Test
    void constructor() {
        // Given
        final long id = CommentFixture.DEFAULT_ID;
        final UserEntity user = UserFixture.getUser();
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();
        final String content = CommentFixture.DEFAULT_CONTENT;
        final LocalDateTime createdAt = CommentFixture.DEFAULT_DATE_TIME;
        final LocalDateTime updatedAt = CommentFixture.DEFAULT_DATE_TIME;
        final boolean isDisplayed = CommentFixture.DEFAULT_DISPLAYED;

        // When
        Comment comment = new Comment(id, user, articleId, content, createdAt, updatedAt, isDisplayed);

        // Then
        assertAll(
                () -> assertThat(comment.getId()).isEqualTo(id),
                () -> assertThat(comment.getUser()).isEqualTo(user),
                () -> assertThat(comment.getArticleId()).isEqualTo(articleId),
                () -> assertThat(comment.getContent()).isEqualTo(content),
                () -> assertThat(comment.getCreatedAt()).isEqualTo(createdAt),
                () -> assertThat(comment.getUpdatedAt()).isEqualTo(updatedAt),
                () -> assertThat(comment.getDisplayed()).isEqualTo(isDisplayed)
        );
    }

    @Test
    @DisplayName("댓글을 생성한다.")
    void create() {
        // Given
        final UserEntity user = UserFixture.getUser();
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();
        final String content = CommentFixture.DEFAULT_CONTENT;

        // When
        final Comment result = Comment.create(user, articleId, content);

        // Then
        assertAll(
                () -> assertThat(result.getUser()).isEqualTo(user),
                () -> assertThat(result.getArticleId()).isEqualTo(articleId),
                () -> assertThat(result.getContent()).isEqualTo(content)
        );
    }

    @Test
    @DisplayName("댓글 생성 시, 본문이 NULL 혹은 공백인 경우 예외를 발생한다.")
    void createWithInvalidContent() {
        // Given
        final UserEntity user = UserFixture.getUser();
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();
        final String content = "";

        // Then & When
        assertThatThrownBy(() -> Comment.create(user, articleId, content))
                .isInstanceOf(RestApiException.class)
                .withFailMessage("댓글 본문은 반드시 입력되어야 합니다.");
    }

    @Test
    @DisplayName("댓글을 수정한다.")
    void update() {
        // Given
        final Comment comment = CommentFixture.getComment();

        // When
        comment.updateContent(CommentFixture.DEFAULT_NEW_CONTENT);

        // Then
        assertThat(comment.getContent()).isEqualTo(CommentFixture.DEFAULT_NEW_CONTENT);
    }

    @Test
    @DisplayName("댓글 수정 시, 본문이 NULL 혹은 공백인 경우 예외를 발생한다.")
    void updateWithInvalidContent() {
        // Given
        final Comment comment = CommentFixture.getComment();

        // Then & When
        assertThatThrownBy(() -> comment.updateContent(""))
                .isInstanceOf(RestApiException.class)
                .withFailMessage("댓글 본문은 반드시 입력되어야 합니다.");
    }

    @Test
    @DisplayName("댓글을 비공개 처리한다.")
    void hide() {
        // Given
        final Comment comment = CommentFixture.getComment();

        // When
        comment.hide();

        // Then
        assertThat(comment.getDisplayed()).isFalse();
    }

}