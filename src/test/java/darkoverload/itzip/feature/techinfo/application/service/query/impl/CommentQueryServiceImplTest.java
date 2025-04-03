package darkoverload.itzip.feature.techinfo.application.service.query.impl;

import darkoverload.itzip.feature.techinfo.ui.payload.response.CommentResponse;
import darkoverload.itzip.feature.techinfo.application.service.query.CommentQueryService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.fixture.ArticleFixture;
import darkoverload.itzip.global.fixture.CommentFixture;
import darkoverload.itzip.global.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SqlGroup({
        @Sql(scripts = "/sql/techinfo/default-insert-comment-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/techinfo/default-delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@SpringBootTest
@ActiveProfiles("test")
class CommentQueryServiceImplTest {

    @Autowired
    private CommentQueryService commentQueryService;

    @Test
    @DisplayName("특정 아티클 ID에 대한 댓글 목록을 반환한다.")
    void getCommentsByArticleId() {
        // When
        final Page<CommentResponse> results = commentQueryService.getCommentsByArticleId(ArticleFixture.DEFAULT_ID.toHexString(), 0, 12);

        // Then
        assertThat(results.getTotalElements()).isEqualTo(1);
        results.forEach(result ->
                assertAll(
                        () -> assertThat(result.profileImageUrI()).isEqualTo(UserFixture.DEFAULT_PROFILE_IMAGE_URI),
                        () -> assertThat(result.nickname()).isEqualTo(UserFixture.DEFAULT_NICKNAME),
                        () -> assertThat(result.commentId()).isEqualTo(CommentFixture.DEFAULT_ID),
                        () -> assertThat(result.content()).isEqualTo(CommentFixture.DEFAULT_CONTENT),
                        () -> assertThat(result.createAt()).isEqualTo(CommentFixture.DEFAULT_DATE_TIME.toString())
                )
        );
    }

    @Test
    @DisplayName("특정 아티클 ID에 댓글이 존재하지 않은 경우 예외가 발생한다.")
    void getCommentsByArticleIdWithNonExistentComment() {
        // When & Then
        assertThatThrownBy(() -> commentQueryService.getCommentsByArticleId("", 0, 12))
                .isInstanceOf(RestApiException.class)
                .extracting("exceptionCode")
                .isEqualTo(CommonExceptionCode.COMMENT_NOT_FOUND);
    }

}
