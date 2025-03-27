package darkoverload.itzip.feature.techinfo.domain.entity;

import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleTypeTest {

    @Test
    @DisplayName("존재하지 않는 아티클 타입이 주어졌을 때 예외를 발생한다.")
    void validate() {
        // When & Then
        assertThatThrownBy(() -> ArticleType.validate("00000000000000000000000"))
                .isInstanceOf(RestApiException.class)
                .withFailMessage("아티클 타입을 찾을 수 없습니다.");
    }

}
