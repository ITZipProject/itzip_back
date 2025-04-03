package darkoverload.itzip.feature.techinfo.domain.entity;

import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.global.fixture.ArticleFixture;
import darkoverload.itzip.global.fixture.LikeFixture;
import darkoverload.itzip.global.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class LikeTest {

    @Test
    void constructor() {
        // Given
        final long id = LikeFixture.DEFAULT_ID;
        final UserEntity user = UserFixture.getUser();
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();
        final LocalDateTime createdAt = LikeFixture.DEFAULT_DATE_TIME;

        // When
        final Like like = new Like(id, user, articleId, createdAt);

        // Then
        assertAll(
                () -> assertThat(like.getId()).isEqualTo(id),
                () -> assertThat(like.getUser()).isEqualTo(user),
                () -> assertThat(like.getArticleId()).isEqualTo(articleId),
                () -> assertThat(like.getCreatedAt()).isEqualTo(createdAt)
        );
    }

    @Test
    @DisplayName("좋아요을 생성한다.")
    void create() {
        // Given
        final UserEntity user = UserFixture.getUser();
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When
        final Like result = Like.create(user, articleId);

        // Then
        assertAll(
                () -> assertThat(result.getUser()).isEqualTo(user),
                () -> assertThat(result.getArticleId()).isEqualTo(articleId)
        );
    }

}
