package darkoverload.itzip.feature.techinfo.application.service.query.impl;

import darkoverload.itzip.feature.techinfo.application.service.query.ScrapQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;

@SqlGroup({
        @Sql(scripts = "/sql/techinfo/default-insert-scrap-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/techinfo/default-delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@SpringBootTest
@ActiveProfiles("test")
class ScrapQueryServiceImplTest {

    @Autowired
    private ScrapQueryService scrrapQueryService;

    @ParameterizedTest(name = "회원 닉네임: {0}, 아티클 ID: {1}")
    @CsvSource({
            "hyoseung, 67d2b940d88d2b9236a1fb0e"
    })
    @DisplayName("스크랩이 존재하는 경우, true를 반환한다.")
    void existsByUserNicknameAndArticleId(final String userNickname, final String articleId) {
        // When
        final boolean result = scrrapQueryService.existsByUserNicknameAndArticleId(userNickname, articleId);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @ParameterizedTest(name = "회원 닉네임: {0}, 아티클 ID: {1}")
    @CsvSource({
            "rowing, 67d2b940d88d2b9236a1fb0e"
    })
    @DisplayName("스크랩이 존재하지 않는 경우, false를 반환한다.")
    void existsByUserNicknameAndArticleIdWithNonExistentUserNickname(final String userNickname, final String articleId) {
        // When
        final boolean result = scrrapQueryService.existsByUserNicknameAndArticleId(userNickname, articleId);

        // Then
        assertThat(result).isEqualTo(false);
    }

}
