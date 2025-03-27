package darkoverload.itzip.feature.techinfo.application.service.query.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.event.payload.ViewedEvent;
import darkoverload.itzip.feature.techinfo.ui.payload.response.ArticleResponse;
import darkoverload.itzip.feature.techinfo.application.service.query.ArticleQueryService;
import darkoverload.itzip.feature.techinfo.application.service.query.BlogQueryService;
import darkoverload.itzip.feature.techinfo.application.service.query.LikeQueryService;
import darkoverload.itzip.feature.techinfo.application.service.query.ScrapQueryService;
import darkoverload.itzip.feature.techinfo.domain.entity.Article;
import darkoverload.itzip.feature.techinfo.domain.repository.ArticleRepository;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom.impl.YearlyArticleStatistics;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.fixture.ArticleFixture;
import darkoverload.itzip.global.fixture.BlogFixture;
import darkoverload.itzip.global.fixture.CustomUserDetailsFixture;
import darkoverload.itzip.global.fixture.UserFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SqlGroup({
        @Sql(scripts = "/sql/techinfo/default-insert-article-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/techinfo/default-delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@SpringBootTest
@ActiveProfiles("test")
class ArticleQueryServiceImplTest {

    private final ArticleQueryService articleQueryService;

    private final ArticleRepository articleRepository;

    private final Executor asyncExecutor;

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ArticleQueryServiceImplTest(
            ArticleRepository articleRepository,
            BlogQueryService blogQueryService,
            LikeQueryService likeQueryService,
            ScrapQueryService scrapQueryService,
            @Qualifier("asyncExecutor") Executor asyncExecutor,
            ApplicationEventPublisher eventPublisher
    ) {
        this.articleRepository = articleRepository;
        this.asyncExecutor = spy(asyncExecutor);
        this.eventPublisher = spy(eventPublisher);
        articleQueryService = new ArticleQueryServiceImpl(articleRepository, blogQueryService, likeQueryService, scrapQueryService, this.asyncExecutor, this.eventPublisher);
    }

    @BeforeEach
    public void setUp() {
        final Article article = ArticleFixture.getSavedArticle();
        articleRepository.save(article);
    }

    @AfterEach
    public void tearDown() {
        articleRepository.deleteById(ArticleFixture.DEFAULT_ID);
    }

    @Test
    @DisplayName("사용자 정보가 없을 경우, 아티클 조회 시 이벤트 발행은 1회, 비동기 작업은 실행되지 않아야 한다.")
    void getArticleByIdWithoutUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;

        // When
        final ArticleResponse result = articleQueryService.getArticleById(userDetails, ArticleFixture.DEFAULT_ID.toHexString());

        // Then
        assertAll(
                () -> assertThat(result.author()).isEqualTo(UserFixture.DEFAULT_NICKNAME),
                () -> assertThat(result.profileImageUri()).isEqualTo(UserFixture.DEFAULT_PROFILE_IMAGE_URI),
                () -> assertThat(result.articleId()).isEqualTo(ArticleFixture.DEFAULT_ID.toHexString()),
                () -> assertThat(result.blogId()).isEqualTo(BlogFixture.DEFAULT_ID),
                () -> assertThat(result.type()).isEqualTo(ArticleFixture.DEFAULT_TYPE.name().toLowerCase()),
                () -> assertThat(result.title()).isEqualTo(ArticleFixture.DEFAULT_TITLE),
                () -> assertThat(result.content()).isEqualTo(ArticleFixture.DEFAULT_CONTENT),
                () -> assertThat(result.thumbnailImageUri()).isEqualTo(ArticleFixture.DEFAULT_THUMBNAIL_URI),
                () -> assertThat(result.likesCount()).isEqualTo(ArticleFixture.DEFAULT_LIKES_COUNT),
                () -> assertThat(result.viewCount()).isEqualTo(ArticleFixture.DEFAULT_VIEW_COUNT),
                () -> assertThat(result.createdAt()).isEqualTo(ArticleFixture.DEFAULT_DATE_TIME.toString()),
                () -> assertThat(result.isLiked()).isFalse(),
                () -> assertThat(result.isScrapped()).isFalse()
        );

        verify(eventPublisher, times(1)).publishEvent(any(ViewedEvent.class));
        verify(asyncExecutor, times(0)).execute(any(Runnable.class));
    }

    @Test
    @DisplayName("사용자 정보가 제공된 경우, 아티클 조회 시 이벤트 발행은 1회, 비동기 작업은 최소 2회 실행되어야 한다.")
    void getArticleByIdWithUserDetails() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();

        // When
        final ArticleResponse result = articleQueryService.getArticleById(userDetails, ArticleFixture.DEFAULT_ID.toHexString());

        // Then
        assertAll(
                () -> assertThat(result.author()).isEqualTo(UserFixture.DEFAULT_NICKNAME),
                () -> assertThat(result.profileImageUri()).isEqualTo(UserFixture.DEFAULT_PROFILE_IMAGE_URI),
                () -> assertThat(result.articleId()).isEqualTo(ArticleFixture.DEFAULT_ID.toHexString()),
                () -> assertThat(result.blogId()).isEqualTo(BlogFixture.DEFAULT_ID),
                () -> assertThat(result.type()).isEqualTo(ArticleFixture.DEFAULT_TYPE.name().toLowerCase()),
                () -> assertThat(result.title()).isEqualTo(ArticleFixture.DEFAULT_TITLE),
                () -> assertThat(result.content()).isEqualTo(ArticleFixture.DEFAULT_CONTENT),
                () -> assertThat(result.thumbnailImageUri()).isEqualTo(ArticleFixture.DEFAULT_THUMBNAIL_URI),
                () -> assertThat(result.likesCount()).isEqualTo(ArticleFixture.DEFAULT_LIKES_COUNT),
                () -> assertThat(result.viewCount()).isEqualTo(ArticleFixture.DEFAULT_VIEW_COUNT),
                () -> assertThat(result.createdAt()).isEqualTo(ArticleFixture.DEFAULT_DATE_TIME.toString()),
                () -> assertThat(result.isLiked()).isTrue(),
                () -> assertThat(result.isScrapped()).isTrue()
        );

        verify(eventPublisher, times(1)).publishEvent(any(ViewedEvent.class));
        verify(asyncExecutor, times(2)).execute(any(Runnable.class));
    }

    @Test
    @DisplayName("존재하지 않는 아티클 ID 조회 시 예외를 발생한다.")
    void getArticleByIdWithInvalidArticleId() {
        // Given
        final CustomUserDetails userDetails = null;

        // When & Then
        assertThatThrownBy(() -> articleQueryService.getArticleById(userDetails, "000000000000000000000000"))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.ARTICLE_NOT_FOUND);
    }

    @Test
    @DisplayName("아티클 미리보기를 타입 필터가 누락인 상태로 조회한다")
    void getArticlesPreviewByTypeWithInvalidType() {
        // When
        final Page<ArticleResponse> results = articleQueryService.getArticlesPreviewByType(null, 0, 12, "newest");

        // Then
        assertThat(results.getTotalElements()).isEqualTo(1);
        results.forEach(result ->
            assertAll(
                    () -> assertThat(result.author()).isEqualTo(UserFixture.DEFAULT_NICKNAME),
                    () -> assertThat(result.profileImageUri()).isEqualTo(UserFixture.DEFAULT_PROFILE_IMAGE_URI),
                    () -> assertThat(result.articleId()).isEqualTo(ArticleFixture.DEFAULT_ID.toHexString()),
                    () -> assertThat(result.type()).isEqualTo(ArticleFixture.DEFAULT_TYPE.name().toLowerCase()),
                    () -> assertThat(result.title()).isEqualTo(ArticleFixture.DEFAULT_TITLE),
                    () -> assertThat(result.content()).hasSize(300),
                    () -> assertThat(result.thumbnailImageUri()).isEqualTo(ArticleFixture.DEFAULT_THUMBNAIL_URI),
                    () -> assertThat(result.likesCount()).isEqualTo(ArticleFixture.DEFAULT_LIKES_COUNT),
                    () -> assertThat(result.createdAt()).isEqualTo(ArticleFixture.DEFAULT_DATE_TIME.toString())
            )
        );
    }

    @Test
    @DisplayName("아티클 미리보기를 특정 타입으로 조회한다.")
    void getArticlesPreviewByTypeWithValidType() {
        // When
        final Page<ArticleResponse> results = articleQueryService.getArticlesPreviewByType("other", 0, 12, "newest");

        // Then
        assertThat(results.getTotalElements()).isEqualTo(1);
        results.forEach(result ->
            assertAll(
                    () -> assertThat(result.author()).isEqualTo(UserFixture.DEFAULT_NICKNAME),
                    () -> assertThat(result.profileImageUri()).isEqualTo(UserFixture.DEFAULT_PROFILE_IMAGE_URI),
                    () -> assertThat(result.articleId()).isEqualTo(ArticleFixture.DEFAULT_ID.toHexString()),
                    () -> assertThat(result.type()).isEqualTo(ArticleFixture.DEFAULT_TYPE.name().toLowerCase()),
                    () -> assertThat(result.title()).isEqualTo(ArticleFixture.DEFAULT_TITLE),
                    () -> assertThat(result.content()).hasSize(300),
                    () -> assertThat(result.thumbnailImageUri()).isEqualTo(ArticleFixture.DEFAULT_THUMBNAIL_URI),
                    () -> assertThat(result.likesCount()).isEqualTo(ArticleFixture.DEFAULT_LIKES_COUNT),
                    () -> assertThat(result.createdAt()).isEqualTo(ArticleFixture.DEFAULT_DATE_TIME.toString())
            )
        );
    }

    @Test
    @DisplayName("아티클 미리보기를 특정 타입으로 조회 시 존재하지 않는 경우 예외를 발생한다.")
    void getArticlesPreviewByTypeWithNonExistentArticle() {
        // When & Then
        assertThatThrownBy(() -> articleQueryService.getArticlesPreviewByType("tech_ai", 0, 12, "newest"))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.ARTICLE_NOT_FOUND);
    }

    @Test
    @DisplayName("아티클 미리보기를 닉네임 기반으로 조회한다.")
    void getArticlesPreviewByAuthor() {
        // Given
        final String nickname = UserFixture.DEFAULT_NICKNAME;

        // When
        final Page<ArticleResponse> results = articleQueryService.getArticlesPreviewByAuthor(nickname, 0, 12, "newest");

        // Then
        assertThat(results.getTotalElements()).isEqualTo(1);
        results.forEach(result ->
                assertAll(
                        () -> assertThat(result.articleId()).isEqualTo(ArticleFixture.DEFAULT_ID.toHexString()),
                        () -> assertThat(result.type()).isEqualTo(ArticleFixture.DEFAULT_TYPE.name().toLowerCase()),
                        () -> assertThat(result.title()).isEqualTo(ArticleFixture.DEFAULT_TITLE),
                        () -> assertThat(result.content()).hasSize(300),
                        () -> assertThat(result.thumbnailImageUri()).isEqualTo(ArticleFixture.DEFAULT_THUMBNAIL_URI),
                        () -> assertThat(result.likesCount()).isEqualTo(ArticleFixture.DEFAULT_LIKES_COUNT),
                        () -> assertThat(result.createdAt()).isEqualTo(ArticleFixture.DEFAULT_DATE_TIME.toString())
                )
        );
    }

    @Test
    @DisplayName("아티클 미리보기를 닉네임 기반으로 조회 시 존재하지 않는 경우 예외를 발생한다.")
    void getArticlesPreviewByAuthorWithNonExistentArticle() {
        // Given
        articleRepository.deleteById(ArticleFixture.DEFAULT_ID);
        final String nickname = UserFixture.DEFAULT_NICKNAME;

        // When & Then
        assertThatThrownBy(() -> articleQueryService.getArticlesPreviewByAuthor(nickname, 0, 12, "newest"))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.ARTICLE_NOT_FOUND);
    }

    @Test
    @DisplayName("블로그 ID 기반 연도별 아티클 통계를 조회한다.")
    void getYearlyArticleStatisticsByBlogId() {
        // Given
        final long blogId = BlogFixture.DEFAULT_ID;

        // When
        final List<YearlyArticleStatistics> stats = articleQueryService.getYearlyArticleStatisticsByBlogId(blogId);

        // Then
        assertThat(stats.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("아티클 존재 여부를 조회한다.")
    void existsById() {
        // When
        final boolean exists = articleQueryService.existsById(ArticleFixture.DEFAULT_ID.toHexString());

        // Then
        assertThat(exists).isTrue();
    }

}
