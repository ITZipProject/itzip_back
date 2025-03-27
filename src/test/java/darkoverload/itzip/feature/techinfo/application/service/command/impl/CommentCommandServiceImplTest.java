package darkoverload.itzip.feature.techinfo.application.service.command.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.service.command.CommentCommandService;
import darkoverload.itzip.feature.techinfo.domain.entity.Comment;
import darkoverload.itzip.feature.techinfo.domain.repository.ArticleRepository;
import darkoverload.itzip.feature.techinfo.domain.repository.CommentRepository;
import darkoverload.itzip.feature.techinfo.ui.payload.request.comment.CommentEditRequest;
import darkoverload.itzip.feature.techinfo.ui.payload.request.comment.CommentRegistrationRequest;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.fixture.ArticleFixture;
import darkoverload.itzip.global.fixture.CommentFixture;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SqlGroup({
        @Sql(scripts = "/sql/techinfo/custom/custom-insert-comment-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/techinfo/custom/custom-delete-comment-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@SpringBootTest
@ActiveProfiles("test")
class CommentCommandServiceImplTest {

    @Autowired
    private CommentCommandService commentCommandService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        articleRepository.save(ArticleFixture.getSavedArticle());
    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteById(ArticleFixture.DEFAULT_ID);
    }

    @Test
    @DisplayName("댓글을 생성한다.")
    void create() {
        // Given
        final CustomUserDetails userDetails = new CustomUserDetails(UserFixture.SECOND_EMAIL, UserFixture.DEFAULT_PASSWORD, UserFixture.SECOND_NICKNAME, List.of(UserFixture.DEFAULT_AUTHORITY));
        final CommentRegistrationRequest request = new CommentRegistrationRequest(ArticleFixture.DEFAULT_ID.toHexString(), CommentFixture.DEFAULT_CONTENT);

        // When
        commentCommandService.create(userDetails, request);

        // Then
        final Comment result = commentRepository.findByUserId(UserFixture.SECOND_ID).get();
        assertAll(
                () -> assertThat(result.getUser().getEmail()).isEqualTo(userDetails.getEmail()),
                () -> assertThat(result.getUser().getNickname()).isEqualTo(userDetails.getNickname()),
                () -> assertThat(result.getArticleId()).isEqualTo(request.articleId()),
                () -> assertThat(result.getContent()).isEqualTo(request.content()),
                () -> assertThat(result.getDisplayed()).isTrue()
        );
    }

    @Test
    @DisplayName("댓글 생성 시 사용자 정보가 누락일 경우 예외가 발생한다.")
    void createInvalidUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;
        final CommentRegistrationRequest request = new CommentRegistrationRequest(ArticleFixture.DEFAULT_ID.toHexString(), CommentFixture.DEFAULT_CONTENT);

        // When & Then
        assertThatThrownBy(() -> commentCommandService.create(userDetails, request))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

    @Test
    @DisplayName("댓글 생성 시 사용자가 존재하지 않은 경우 예외가 발생한다.")
    void createNonExistentUser() {
        // Given
        final CustomUserDetails userDetails = new CustomUserDetails(UserFixture.NON_EXISTENT_EMAIL, UserFixture.DEFAULT_PASSWORD, UserFixture.NON_EXISTENT_NICKNAME, List.of(UserFixture.DEFAULT_AUTHORITY));
        final CommentRegistrationRequest request = new CommentRegistrationRequest(ArticleFixture.DEFAULT_ID.toHexString(), CommentFixture.DEFAULT_CONTENT);

        // When & Then
        assertThatThrownBy(() -> commentCommandService.create(userDetails, request))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.NOT_FOUND_USER);
    }

    @Test
    @DisplayName("댓글 생성 시 아티클이 존재하지 않은 경우 예외가 발생한다.")
    void createWithNonExistentArticleId() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final CommentRegistrationRequest request = new CommentRegistrationRequest(ArticleFixture.NON_EXISTENT_ID.toHexString(), CommentFixture.DEFAULT_CONTENT);

        // When & Then
        assertThatThrownBy(() -> commentCommandService.create(userDetails, request))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.ARTICLE_NOT_FOUND);
    }

    @Test
    @DisplayName("댓글을 변경한다.")
    void update() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final CommentEditRequest request = new CommentEditRequest(CommentFixture.DEFAULT_ID, CommentFixture.DEFAULT_NEW_CONTENT);

        // When
        commentCommandService.update(userDetails, request);

        // Then
        final Comment result = commentRepository.findById(request.commentId()).get();
        assertThat(result.getContent()).isEqualTo(request.content());
    }

    @Test
    @DisplayName("댓글 변경 시 사용자 정보가 누락일 경우 예외가 발생한다.")
    void updateInvalidUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;
        final CommentEditRequest request = new CommentEditRequest(CommentFixture.DEFAULT_ID, CommentFixture.DEFAULT_NEW_CONTENT);

        // When & Then
        assertThatThrownBy(() -> commentCommandService.update(userDetails, request))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

    @Test
    @DisplayName("댓글 변경 시 요청된 회원의 댓글 정보가 존재하지 않은 경우 예외가 발생한다.")
    void updateWithNonExistentCommentId() {
        // Given
        final long nonExistentCommentId = CommentFixture.NON_EXISTENT_COMMENT_ID;
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final CommentEditRequest request = new CommentEditRequest(nonExistentCommentId, CommentFixture.DEFAULT_NEW_CONTENT);

        // When & Then
        assertThatThrownBy(() -> commentCommandService.update(userDetails, request))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.COMMENT_NOT_FOUND);
    }

    @Test
    @DisplayName("댓글을 삭제(숨김) 한다.")
    void delete() {
        // Given
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final long commentId = CommentFixture.DEFAULT_ID;

        // When
        commentCommandService.delete(userDetails, commentId);

        // Then
        final Comment result = commentRepository.findById(commentId).get();
        assertThat(result.getDisplayed()).isFalse();
    }

    @Test
    @DisplayName("댓글 삭제(숨김) 시 사용자 정보가 누락일 경우 예외가 발생한다.")
    void deleteInvalidUserDetails() {
        // Given
        final CustomUserDetails userDetails = null;
        final long commentId = CommentFixture.DEFAULT_ID;

        // When & Then
        assertThatThrownBy(() -> commentCommandService.delete(userDetails, commentId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.UNAUTHORIZED);
    }

    @Test
    @DisplayName("댓글 삭제(숨김) 시 요청된 회원의 댓글 정보가 존재하지 않은 경우 예외가 발생한다.")
    void deleteWithNonExistentCommentId() {
        final CustomUserDetails userDetails = CustomUserDetailsFixture.getCustomUserDetails();
        final long nonExistentCommentId = CommentFixture.NON_EXISTENT_COMMENT_ID;

        // When & Then
        assertThatThrownBy(() -> commentCommandService.delete(userDetails, nonExistentCommentId))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.COMMENT_NOT_FOUND);
    }

    @Test
    @DisplayName("아티클 ID와 관련된 댓글을 모두 삭제(숨김) 처리한다.")
    void deleteByArticleId() {
        // Given
        final String articleIdHex = ArticleFixture.DEFAULT_ID.toHexString();

        // When
        commentCommandService.deleteByArticleId(articleIdHex);

        // Then
        final Comment result = commentRepository.findById(CommentFixture.DEFAULT_ID).get();
        assertThat(result.getDisplayed()).isFalse();
    }

}
