package darkoverload.itzip.feature.techinfo.domain.entity;

import darkoverload.itzip.feature.techinfo.domain.projection.ArticlePreview;
import darkoverload.itzip.global.fixture.ArticleFixture;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ArticlePreviewTest {

    private class DummyArticlePreview implements ArticlePreview {

        private final ObjectId id;
        private final long blogId;
        private final ArticleType type;
        private final String title;
        private final String content;
        private final String thumbnailImageUrl;
        private final long likesCount;
        private final LocalDateTime createdAt;

        public DummyArticlePreview(final Article article) {
            this.id = article.getId();
            this.blogId = article.getBlogId();
            this.type = article.getType();
            this.title = article.getTitle();
            this.content = article.getContent();
            this.thumbnailImageUrl = article.getThumbnailImageUri();
            this.likesCount = article.getLikesCount();
            this.createdAt = article.getCreatedAt();
        }

        @Override
        public ObjectId getId() {
            return this.id;
        }

        @Override
        public long getBlogId() {
            return this.blogId;
        }

        @Override
        public ArticleType getType() {
            return this.type;
        }

        @Override
        public String getTitle() {
            return this.title;
        }

        @Override
        public String getContent() {
            return this.content;
        }

        @Override
        public String getThumbnailImageUri() {
            return this.thumbnailImageUrl;
        }

        @Override
        public long getLikesCount() {
            return this.likesCount;
        }

        @Override
        public LocalDateTime getCreatedAt() {
            return this.createdAt;
        }

    }

    @Test
    public void ArticlePreviewGetters() {
        // Given
        final Article article = ArticleFixture.getNewArticle();

        // When
        ArticlePreview result = new DummyArticlePreview(article);

        // Then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(article.getId()),
                () -> assertThat(result.getBlogId()).isEqualTo(article.getBlogId()),
                () -> assertThat(result.getTitle()).isEqualTo(article.getTitle()),
                () -> assertThat(result.getContent()).isEqualTo(article.getContent()),
                () -> assertThat(result.getThumbnailImageUri()).isEqualTo(article.getThumbnailImageUri()),
                () -> assertThat(result.getLikesCount()).isEqualTo(article.getLikesCount()),
                () -> assertThat(result.getCreatedAt()).isEqualTo(article.getCreatedAt())
        );
    }

}
