package darkoverload.itzip.feature.techinfo.domain.entity;

import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.fixture.ArticleFixture;
import darkoverload.itzip.global.fixture.BlogFixture;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class ArticleTest {

    @Test
    void constructor() {
        // Given
        final ObjectId id = ArticleFixture.DEFAULT_ID;
        final long blogId = BlogFixture.DEFAULT_ID;
        final ArticleType type = ArticleFixture.DEFAULT_TYPE;
        final String title = ArticleFixture.DEFAULT_TITLE;
        final String content = ArticleFixture.DEFAULT_CONTENT;
        final String thumbnailImageUri = ArticleFixture.DEFAULT_THUMBNAIL_URI;
        final long likesCount = ArticleFixture.DEFAULT_LIKES_COUNT;
        final long viewCount = ArticleFixture.DEFAULT_VIEW_COUNT;
        final LocalDateTime createdAt = ArticleFixture.DEFAULT_DATE_TIME;
        final LocalDateTime updatedAt = ArticleFixture.DEFAULT_DATE_TIME;
        final boolean displayed = ArticleFixture.DEFAULT_DISPLAYED;

        // When
        final Article article = new Article(id, blogId, type, title, content, thumbnailImageUri, 0, 0, createdAt, updatedAt, displayed);

        // Then
        assertAll(
                () -> assertThat(article.getId()).isEqualTo(id),
                () -> assertThat(article.getBlogId()).isEqualTo(blogId),
                () -> assertThat(article.getType()).isEqualTo(type),
                () -> assertThat(article.getTitle()).isEqualTo(title),
                () -> assertThat(article.getContent()).isEqualTo(content),
                () -> assertThat(article.getThumbnailImageUri()).isEqualTo(thumbnailImageUri),
                () -> assertThat(article.getLikesCount()).isEqualTo(likesCount),
                () -> assertThat(article.getViewCount()).isEqualTo(viewCount),
                () -> assertThat(article.getCreatedAt()).isEqualTo(createdAt),
                () -> assertThat(article.getDisplayed()).isEqualTo(displayed)
        );
    }

    @Test
    @DisplayName("아티클을 생성한다.")
    void create() {
        // Given
        final Long blogId = BlogFixture.DEFAULT_ID;
        final String type = ArticleFixture.DEFAULT_TYPE.toString();
        final String title = ArticleFixture.DEFAULT_TITLE;
        final String content = ArticleFixture.DEFAULT_CONTENT;
        final String thumbnailImageUri = ArticleFixture.DEFAULT_THUMBNAIL_URI;

        // When
        final Article result = Article.create(blogId, type, title, content, thumbnailImageUri);

        // Then
        assertAll(
                () -> assertThat(result.getBlogId()).isEqualTo(blogId),
                () -> assertThat(result.getType()).isEqualTo(ArticleType.from(type)),
                () -> assertThat(result.getTitle()).isEqualTo(title),
                () -> assertThat(result.getContent()).isEqualTo(content),
                () -> assertThat(result.getThumbnailImageUri()).isEqualTo(thumbnailImageUri),
                () -> assertThat(result.getDisplayed()).isEqualTo(ArticleFixture.DEFAULT_DISPLAYED)
        );
    }

    @Test
    @DisplayName("아티클 생성 시, 제목이 NULL 혹은 공백인 경우 예외를 발생한다.")
    void createWithInvalidTitle() {
        // Given
        final Long blogId = BlogFixture.DEFAULT_ID;
        final String type = ArticleFixture.DEFAULT_TYPE.toString();
        final String title = "";
        final String content = ArticleFixture.DEFAULT_CONTENT;
        final String thumbnailImageUri = ArticleFixture.DEFAULT_THUMBNAIL_URI;

        // When & Then
        assertThatThrownBy(() -> Article.create(blogId, type, title, content, thumbnailImageUri))
                .isInstanceOf(RestApiException.class)
                .withFailMessage("아티클 제목은 반드시 입력되어야 합니다.");
    }

    @Test
    @DisplayName("아티클을 수정한다.")
    void update() {
        // Given
        final Article article = ArticleFixture.getNewArticle();
        final String changedType = "software_development_programming_language";
        final String newTitle = "new title";
        final String newContent = "new content";
        final String newThumbnailImageUri = "new thumbnail image";

        // When
        final Article result = article.update(changedType, newTitle, newContent, newThumbnailImageUri);

        // Then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(article.getId()),
                () -> assertThat(result.getBlogId()).isEqualTo(article.getBlogId()),
                () -> assertThat(result.getType()).isEqualTo(ArticleType.SOFTWARE_DEVELOPMENT_PROGRAMMING_LANGUAGE),
                () -> assertThat(result.getTitle()).isEqualTo(newTitle),
                () -> assertThat(result.getContent()).isEqualTo(newContent),
                () -> assertThat(result.getThumbnailImageUri()).isEqualTo(newThumbnailImageUri),
                () -> assertThat(result.getDisplayed()).isEqualTo(article.getDisplayed())
        );
    }

    @Test
    @DisplayName("아티클 수정 시, 제목이 NULL 혹은 공백인 경우 예외를 발생한다.")
    void updateWithInvalidTitle() {
        // Given
        final Article article = ArticleFixture.getNewArticle();
        final String changedType = "software_development_programming_language";
        final String newTitle = "";
        final String newContent = "new content";
        final String newThumbnailImageUri = "new thumbnail image";

        // When & Then
        assertThatThrownBy(() -> article.update(changedType, newTitle, newContent, newThumbnailImageUri))
                .isInstanceOf(RestApiException.class)
                .withFailMessage("아티클 제목은 반드시 입력되어야 합니다.");
    }

    @Test
    @DisplayName("아티클을 비공개한다.")
    void hide() {
        // Given
        final Article article = ArticleFixture.getNewArticle();

        // When
        article.hide();

        // Then
        assertThat(article.getDisplayed()).isFalse();
    }

}
