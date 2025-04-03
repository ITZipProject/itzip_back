package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.techinfo.domain.entity.Scrap;

import java.time.LocalDateTime;

/**
 * 테스트용 Scrap 엔티티의 Fixture 데이터를 제공하는 클래스입니다.
 *
 * <p>
 *     Scrap 관련 테스트에 사용될 기본 값들을 상수로 정의하고,
 *     Scrap 객체를 생성하는 헬퍼 메서드를 제공합니다.
 * </p>
 */
public class ScrapFixture {

    private ScrapFixture() {
    }

    public static final long DEFAULT_ID = 999L;
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(2025, 3, 4, 0, 0, 0);

    public static Scrap getScrap() {
        return new Scrap(UserFixture.getUser(), ArticleFixture.DEFAULT_ID.toHexString());
    }

}
