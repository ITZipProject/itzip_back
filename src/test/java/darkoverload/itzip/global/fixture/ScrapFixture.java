package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.techinfo.domain.entity.Scrap;

import java.time.LocalDateTime;

public class ScrapFixture {

    private ScrapFixture() {
    }

    public static final long DEFAULT_ID = 999L;
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(2025, 3, 4, 0, 0, 0);

    public static Scrap getScrap() {
        return new Scrap(UserFixture.getUser(), ArticleFixture.DEFAULT_ID.toHexString());
    }

}
