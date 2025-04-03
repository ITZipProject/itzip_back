package darkoverload.itzip.feature.techinfo.application.service.command.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.event.payload.LikeCancelledEvent;
import darkoverload.itzip.feature.techinfo.application.event.payload.LikedEvent;
import darkoverload.itzip.feature.techinfo.application.service.command.LikeCommandService;
import darkoverload.itzip.feature.techinfo.application.service.query.ArticleQueryService;
import darkoverload.itzip.feature.techinfo.domain.entity.Like;
import darkoverload.itzip.feature.techinfo.domain.repository.ArticleRepository;
import darkoverload.itzip.feature.techinfo.domain.repository.LikeRepository;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.fixture.ArticleFixture;
import darkoverload.itzip.global.fixture.CustomUserDetailsFixture;
import darkoverload.itzip.global.fixture.UserFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@SqlGroup({
        @Sql(scripts = "/sql/techinfo/custom/custom-insert-like-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/techinfo/custom/custom-delete-like-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@SpringBootTest
@ActiveProfiles("test")
class LikeCommandServiceImplTest {

    private final LikeCommandService likeCommandService;

    private final ArticleRepository articleRepository;

    private final LikeRepository likeRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public LikeCommandServiceImplTest(
            final ArticleRepository articleRepository,
            final LikeRepository likeRepository,
            final UserRepository userRepository,
            final ArticleQueryService articleQueryService,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.articleRepository = articleRepository;
        this.likeRepository = likeRepository;
        this.eventPublisher = spy(applicationEventPublisher);
        this.likeCommandService = new LikeCommandServiceImpl(likeRepository, userRepository, articleQueryService, this.eventPublisher);
    }

    @BeforeEach
    void setUp() {
        articleRepository.save(ArticleFixture.getSavedArticle());
    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteById(ArticleFixture.DEFAULT_ID);
    }

    @Test
    @DisplayName("좋아요를 생성한다.")
    void create() {
        // Given
        final CustomUserDetails userDetails = new CustomUserDetails(UserFixture.SECOND_EMAIL, UserFixture.DEFAULT_PASSWORD, UserFixture.SECOND_NICKNAME, List.of(UserFixture.DEFAULT_AUTHORITY));
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When
        likeCommandService.create(userDetails, articleId);

        // Then
        verify(eventPublisher, times(1)).publishEvent(any(LikedEvent.class));

        final Like result = likeRepository.findByUserId(UserFixture.SECOND_ID).get();
        assertAll(
                () -> assertThat(result.getUser().getEmail()).isEqualTo(userDetails.getEmail()),
                () -> assertThat(result.getUser().getPassword()).isEqualTo(userDetails.getPassword()),
                () -> assertThat(result.getUser().getNickname()).isEqualTo(userDetails.getNickname()),
                () -> assertThat(result.getUser().getAuthority()).isEqualTo(userDetails.getAuthorities().stream().findFirst().get()),
                () -> assertThat(result.getArticleId()).isEqualTo(articleId)
        );
    }

    @Test
    @DisplayName("좋아요 생성 시 사용자 정보가 누락일 경우 예외가 발생한다.")
    void createWithInvalidUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When & Then
        assertThatThrownBy(() -> likeCommandService.create(userDetails, articleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

    @Test
    @DisplayName("좋아요 생성 시 사용자가 존재하지 않은 경우 예외가 발생한다.")
    void createWithInvalidUser() {
        // Given
        final CustomUserDetails userDetails = new CustomUserDetails(UserFixture.NON_EXISTENT_EMAIL, UserFixture.DEFAULT_PASSWORD, UserFixture.NON_EXISTENT_NICKNAME, List.of(UserFixture.DEFAULT_AUTHORITY));
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When & Then
        assertThatThrownBy(() -> likeCommandService.create(userDetails, articleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.NOT_FOUND_USER);
    }

    @Test
    @DisplayName("좋아요 생성 시 아티클이 존재하지 않은 경우 예외가 발생한다.")
    void createWithInvalidArticleId() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final String invalidArticleId = ArticleFixture.NON_EXISTENT_ID.toHexString();

        // When
        assertThatThrownBy(() -> likeCommandService.create(userDetails, invalidArticleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.ARTICLE_NOT_FOUND);
    }

    @Test
    @DisplayName("이미 좋아요가 생성된 아티클에 대해 좋아요 생성 요청 시 예외가 발생한다.")
    void createWithAlreadyLikedArticle() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final String alreadyLikedArticleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When
        assertThatThrownBy(() -> likeCommandService.create(userDetails, alreadyLikedArticleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.ALREADY_LIKED_ARTICLE);
    }

    @Test
    @DisplayName("좋아요를 삭제한다.")
    @Transactional
    void delete() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When
        likeCommandService.delete(userDetails, articleId);

        // Then
        verify(eventPublisher, times(1)).publishEvent(any(LikeCancelledEvent.class));
        assertThat(likeRepository.existsByUser_NicknameAndArticleId(userDetails.getNickname(), articleId)).isFalse();
    }

    @Test
    @DisplayName("좋아요 삭제 시 사용자 정보가 누락일 경우 예외가 발생한다.")
    void deleteWithInvalidUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When & Then
        assertThatThrownBy(() -> likeCommandService.delete(userDetails, articleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

}
