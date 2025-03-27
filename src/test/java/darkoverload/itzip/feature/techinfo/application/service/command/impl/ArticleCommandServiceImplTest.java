package darkoverload.itzip.feature.techinfo.application.service.command.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.event.payload.ArticleHiddenEvent;
import darkoverload.itzip.feature.techinfo.application.service.command.ArticleCommandService;
import darkoverload.itzip.feature.techinfo.application.service.query.BlogQueryService;
import darkoverload.itzip.feature.techinfo.domain.entity.Article;
import darkoverload.itzip.feature.techinfo.domain.repository.ArticleRepository;
import darkoverload.itzip.feature.techinfo.ui.payload.request.article.ArticleEditRequest;
import darkoverload.itzip.feature.techinfo.ui.payload.request.article.ArticleRegistrationRequest;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.fixture.ArticleFixture;
import darkoverload.itzip.global.fixture.CustomUserDetailsFixture;
import darkoverload.itzip.global.fixture.UserFixture;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@SqlGroup({
        @Sql(scripts = "/sql/techinfo/default-insert-article-data.sql",  executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/techinfo/default-delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@SpringBootTest
@ActiveProfiles("test")
class ArticleCommandServiceImplTest {

    private final ArticleCommandService articleCommandService;

    private final ArticleRepository articleRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ArticleCommandServiceImplTest(
            final ArticleRepository articleRepository,
            final BlogQueryService blogQueryService,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.articleRepository = articleRepository;
        this.eventPublisher = spy(eventPublisher);
        this.articleCommandService = new ArticleCommandServiceImpl(articleRepository, blogQueryService, this.eventPublisher);
    }

    @BeforeEach
    void setUp() {
        articleRepository.save(ArticleFixture.getSavedArticle());
    }

    @Test
    @DisplayName("아티클을 생성한다.")
    void create() {
        // Given
        final CustomUserDetails userDetails = new CustomUserDetails(UserFixture.DEFAULT_EMAIL, UserFixture.DEFAULT_PASSWORD, UserFixture.DEFAULT_NICKNAME, List.of(UserFixture.DEFAULT_AUTHORITY));
        final ArticleRegistrationRequest request = new ArticleRegistrationRequest(
                ArticleFixture.DEFAULT_TYPE.name().toLowerCase(),
                ArticleFixture.DEFAULT_TITLE,
                ArticleFixture.DEFAULT_CONTENT,
                ArticleFixture.DEFAULT_THUMBNAIL_URI
        );

        // When
        final String response = articleCommandService.create(userDetails, request);

        // Then
        final Article result = articleRepository.findById(new ObjectId(response)).get();
        assertAll(
                () -> assertThat(result.getId().toHexString()).isEqualTo(response),
                () -> assertThat(result.getType().name().toLowerCase()).isEqualTo(request.type()),
                () -> assertThat(result.getTitle()).isEqualTo(request.title()),
                () -> assertThat(result.getContent()).isEqualTo(request.content()),
                () -> assertThat(result.getThumbnailImageUri()).isEqualTo(request.thumbnailImageUri())
        );

        articleRepository.deleteById(result.getId());
    }

    @Test
    @DisplayName("아티클 생성 시 사용자 정보가 누락일 경우 예외가 발생한다.")
    void createWithInvalidUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;
        final ArticleRegistrationRequest request = new ArticleRegistrationRequest(
                ArticleFixture.DEFAULT_TYPE.name().toLowerCase(),
                ArticleFixture.DEFAULT_TITLE,
                ArticleFixture.DEFAULT_CONTENT,
                ArticleFixture.DEFAULT_THUMBNAIL_URI
        );

        // When & Then
        assertThatThrownBy(() -> articleCommandService.create(userDetails, request))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

    @Test
    @DisplayName("아티클을 수정한다.")
    void update() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final ArticleEditRequest request = new ArticleEditRequest(
                ArticleFixture.DEFAULT_ID.toHexString(),
                ArticleFixture.SECOND_TYPE.name().toLowerCase(),
                ArticleFixture.DEFAULT_NEW_TITLE,
                ArticleFixture.DEFAULT_NEW_CONTENT,
                ArticleFixture.DEFAULT_NEW_THUMBNAIL_URI
        );

        // When
        articleCommandService.update(userDetails, request);

        // Then
        final Article result = articleRepository.findById(ArticleFixture.DEFAULT_ID).get();
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(ArticleFixture.DEFAULT_ID),
                () -> assertThat(result.getType().name().toLowerCase()).isEqualTo(request.type()),
                () -> assertThat(result.getTitle()).isEqualTo(request.title()),
                () -> assertThat(result.getContent()).isEqualTo(request.content()),
                () -> assertThat(result.getThumbnailImageUri()).isEqualTo(request.thumbnailImageUri())
        );

        articleRepository.deleteById(result.getId());
    }

    @Test
    @DisplayName("아티클 수정 시 사용자 정보가 누락일 경우 예외가 발생한다.")
    void updateWithInvalidUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;
        final ArticleEditRequest request = new ArticleEditRequest(
                ArticleFixture.DEFAULT_ID.toHexString(),
                ArticleFixture.SECOND_TYPE.name().toLowerCase(),
                ArticleFixture.DEFAULT_NEW_TITLE,
                ArticleFixture.DEFAULT_NEW_CONTENT,
                ArticleFixture.DEFAULT_NEW_THUMBNAIL_URI
        );

        // When & Then
        assertThatThrownBy(() -> articleCommandService.update(userDetails, request))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

    @Test
    @DisplayName("존재하지 않는 아티클 ID 수정 시 예외가 발생한다.")
    void updateWithNonExistentArticleId() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final ArticleEditRequest request = new ArticleEditRequest(
                ArticleFixture.NON_EXISTENT_ID.toHexString(),
                ArticleFixture.SECOND_TYPE.name().toLowerCase(),
                ArticleFixture.DEFAULT_NEW_TITLE,
                ArticleFixture.DEFAULT_NEW_CONTENT,
                ArticleFixture.DEFAULT_NEW_THUMBNAIL_URI
        );

        // When & Then
        assertThatThrownBy(() -> articleCommandService.update(userDetails, request))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.ARTICLE_NOT_FOUND);
    }

    @Test
    @DisplayName("아티클을 삭제(숨김) 한다.")
    void delete() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();

        // When
        articleCommandService.delete(userDetails, ArticleFixture.DEFAULT_ID.toHexString());

        // Then
        verify(eventPublisher, times(1)).publishEvent(any(ArticleHiddenEvent.class));

        final Article result = articleRepository.findById(ArticleFixture.DEFAULT_ID).get();
        assertThat(result.getDisplayed()).isFalse();

        articleRepository.deleteById(result.getId());
    }

    @Test
    @DisplayName("아티클 삭제 시 사용자 정보가 누락일 경우 예외가 발생한다.")
    void deleteWithInvalidUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;

        // When & Then
        assertThatThrownBy(() -> articleCommandService.delete(userDetails, ArticleFixture.DEFAULT_ID.toHexString()))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

    @Test
    @DisplayName("존재하지 않는 아티클 ID 수정 시 예외가 발생한다.")
    void deleteWithNonExistentArticleId() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final String nonExistentArticleId = ArticleFixture.NON_EXISTENT_ID.toHexString();

        // When & Then
        assertThatThrownBy(() -> articleCommandService.delete(userDetails, nonExistentArticleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.ARTICLE_NOT_FOUND);
    }

}
