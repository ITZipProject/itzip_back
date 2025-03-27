package darkoverload.itzip.feature.techinfo.application.service.command.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.service.command.ScrapCommandService;
import darkoverload.itzip.feature.techinfo.domain.entity.Scrap;
import darkoverload.itzip.feature.techinfo.domain.repository.ArticleRepository;
import darkoverload.itzip.feature.techinfo.domain.repository.ScrapRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SqlGroup({
        @Sql(scripts = "/sql/techinfo/custom/custom-insert-scrap-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/techinfo/custom/custom-delete-scrap-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@SpringBootTest
@ActiveProfiles("test")
class ScrapCommandServiceImplTest {

    @Autowired
    private ScrapCommandService scrapCommandService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ScrapRepository scrapRepository;

    @BeforeEach
    void setUp() {
        articleRepository.save(ArticleFixture.getSavedArticle());
    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteById(ArticleFixture.DEFAULT_ID);
    }

    @Test
    @DisplayName("스크랩을 생성한다.")
    void create() {
        // Given
        final CustomUserDetails userDetails = new CustomUserDetails(UserFixture.SECOND_EMAIL, UserFixture.DEFAULT_PASSWORD, UserFixture.SECOND_NICKNAME, List.of(UserFixture.DEFAULT_AUTHORITY));
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When
        scrapCommandService.create(userDetails, articleId);

        // Then
        final Scrap result = scrapRepository.findByUserId(UserFixture.SECOND_ID).get();
        assertAll(
                () -> assertThat(result.getUser().getEmail()).isEqualTo(userDetails.getEmail()),
                () -> assertThat(result.getUser().getPassword()).isEqualTo(userDetails.getPassword()),
                () -> assertThat(result.getUser().getNickname()).isEqualTo(userDetails.getNickname()),
                () -> assertThat(result.getUser().getAuthority()).isEqualTo(userDetails.getAuthorities().stream().findFirst().get()),
                () -> assertThat(result.getArticleId()).isEqualTo(articleId)
        );
    }

    @Test
    @DisplayName("스크랩 생성 시 사용자 정보가 누락일 경우 예외가 발생한다.")
    void createWithInvalidUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When & Then
        assertThatThrownBy(() -> scrapCommandService.create(userDetails, articleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

    @Test
    @DisplayName("스크랩 생성 시 사용자가 존재하지 않은 경우 예외가 발생한다.")
    void createWithInvalidUser() {
        // Given
        final CustomUserDetails userDetails = new CustomUserDetails(UserFixture.NON_EXISTENT_EMAIL, UserFixture.DEFAULT_PASSWORD, UserFixture.NON_EXISTENT_NICKNAME, List.of(UserFixture.DEFAULT_AUTHORITY));
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When & Then
        assertThatThrownBy(() -> scrapCommandService.create(userDetails, articleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.NOT_FOUND_USER);
    }

    @Test
    @DisplayName("스크랩 생성 시 아티클이 존재하지 않은 경우 예외가 발생한다.")
    void createWithInvalidArticleId() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final String invalidArticleId = ArticleFixture.NON_EXISTENT_ID.toHexString();

        // When & Then
        assertThatThrownBy(() -> scrapCommandService.create(userDetails, invalidArticleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.ARTICLE_NOT_FOUND);
    }

    @Test
    @DisplayName("이미 스크랩이 생성된 아티클에 대해 스크랩 생성 요청 시 예외가 발생한다.")
    void createWithAlreadyScrappedArticle() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final String alreadyScrappedArticleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When & Then
        assertThatThrownBy(() -> scrapCommandService.create(userDetails, alreadyScrappedArticleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.ALREADY_SCRAP_ARTICLE);
    }

    @Test
    @DisplayName("스크랩을 삭제한다.")
    @Transactional
    void delete() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When
        scrapCommandService.delete(userDetails, articleId);

        // Then
        assertThat(scrapRepository.existsByUser_NicknameAndArticleId(userDetails.getNickname(), articleId)).isFalse();
    }

    @Test
    @DisplayName("스크랩 삭제 시 사용자 정보가 누락일 경우 예외가 발생한다.")
    void deleteWithInvalidUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;
        final String articleId = ArticleFixture.DEFAULT_ID.toHexString();

        // When & Then
        assertThatThrownBy(() -> scrapCommandService.delete(userDetails, articleId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

}
