package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.techinfo.domain.entity.Like;

import java.time.LocalDateTime;

public class LikeFixture {

    private LikeFixture() {
    }

    public static final long DEFAULT_ID = 999L;
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(2025, 3, 4, 0, 0, 0);

    public static Like getLike() {
        return new Like(UserFixture.getUser(), ArticleFixture.DEFAULT_ID.toHexString());
    }

}
