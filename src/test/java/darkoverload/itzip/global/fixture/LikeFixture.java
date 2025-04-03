package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.techinfo.domain.entity.Like;

import java.time.LocalDateTime;

/**
 * 테스트용 Like 엔티티의 Fixture 데이터를 제공하는 유틸리티 클래스이다.
 *
 * <p>
 *     Like 관련 테스트에서 사용될 기본 값들을 상수로 정의하고,
 *     Like 객체를 생성하는 헬퍼 메서드를 제공합니다.
 * </p>
 */
public class LikeFixture {

    private LikeFixture() {
    }

    public static final long DEFAULT_ID = 999L;
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(2025, 3, 4, 0, 0, 0);

    public static Like getLike() {
        return new Like(UserFixture.getUser(), ArticleFixture.DEFAULT_ID.toHexString());
    }

}
