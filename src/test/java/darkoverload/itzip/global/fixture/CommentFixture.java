package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.techinfo.domain.entity.Comment;

import java.time.LocalDateTime;

/**
 * 테스트용 Comment 엔티티의 Fixture 데이터를 제공하는 클래스입니다.
 *
 * <p>
 *     Comment 관련 테스트에서 사용될 기본 값들을 상수로 정의하고,
 *     Comment 객체를 생성하는 헬퍼 메서드를 제공합니다.
 * </p>
 */
public class CommentFixture {

    private CommentFixture() {
    }

    public static final long DEFAULT_ID = 999;
    public static final long NON_EXISTENT_COMMENT_ID = -1;
    public static final String DEFAULT_CONTENT = "content";
    public static final String DEFAULT_NEW_CONTENT = "new content";
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(2025, 3, 4, 0, 0, 0);
    public static final boolean DEFAULT_DISPLAYED = true;

    public static Comment getComment() {
        return new Comment(UserFixture.getUser(), ArticleFixture.DEFAULT_ID.toHexString(), DEFAULT_CONTENT, DEFAULT_DISPLAYED);
    }

}
