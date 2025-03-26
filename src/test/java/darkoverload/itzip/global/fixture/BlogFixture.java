package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.techinfo.domain.entity.Blog;

import java.time.LocalDateTime;

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
