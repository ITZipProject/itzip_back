package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.techinfo.domain.entity.Blog;

import java.time.LocalDateTime;

/**
 * 테스트용 Blog 엔티티의 Fixture 데이터를 제공하는 클래스입니다.
 *
 * <p>
 *     이 클래스는 Blog 관련 테스트에서 사용될 기본 값들을 상수로 정의하고,
 *     Blog 객체를 생성하는 헬퍼 메서드를 제공합니다.
 * </p>
 */
public class BlogFixture {

    private BlogFixture() {
    }

    public static final long DEFAULT_ID = 999L;
    public static final long SECOND_ID = 1000L;

    public static final String DEFAULT_INTRO = "당신만의 블로그 소개글을 작성해주세요.";
    public static final String DEFAULT_NEW_INTRO = "새로운 블로그 소개글 입니다.";

    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(2025, 3, 4, 0, 0, 0);

    public static Blog getBlog() {
        return new Blog(UserFixture.getUser(), DEFAULT_INTRO);
    }

}
