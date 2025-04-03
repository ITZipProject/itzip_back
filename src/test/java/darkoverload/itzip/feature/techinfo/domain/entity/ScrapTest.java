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

class ScrapTest {

    @Test
    void constructor() {
        // Given
        final long id = LikeFixture.DEFAULT_ID;
        final UserEntity user = UserFixture.getUser();
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();
        final LocalDateTime createdAt = LikeFixture.DEFAULT_DATE_TIME;

        // When
        final Scrap scrap = new Scrap(id, user, articleId, createdAt);

        // Then
        assertAll(
                () -> assertThat(scrap.getId()).isEqualTo(id),
                () -> assertThat(scrap.getUser()).isEqualTo(user),
                () -> assertThat(scrap.getArticleId()).isEqualTo(articleId),
                () -> assertThat(scrap.getCreatedAt()).isEqualTo(createdAt)
        );
    }

    @Test
    @DisplayName("스크랩을 생성한다.")
    void create() {
        // Given
        final UserEntity user = UserFixture.getUser();
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When
        final Scrap result = Scrap.create(user, articleId);

        // Then
        assertAll(
                () -> assertThat(result.getUser()).isEqualTo(user),
                () -> assertThat(result.getArticleId()).isEqualTo(articleId)
        );
    }

}