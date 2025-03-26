package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.techinfo.domain.entity.Comment;

import java.time.LocalDateTime;

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
        return new Comment(UserFixture.getUser(), ArticleFixture.DEFAULT_ID.toHexString(), DEFAULT_CONTENT);
    }

}
