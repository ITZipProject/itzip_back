package darkoverload.itzip.global.fixture;

import darkoverload.itzip.feature.techinfo.domain.entity.Article;
import darkoverload.itzip.feature.techinfo.domain.entity.ArticleType;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * 테스트용 Article 엔티티의 Fixture 데이터를 제공하는 클래스입니다.
 *
 * <p>
 *     이 클래스는 Article 관련 테스트에서 사용될 기본 값들을 상수로 정의하고,
 *     새 Article 객체와 저장된 Article 객체를 생성하는 헬퍼 메서드를 제공합니다.
 * </p>
 */
public class ArticleFixture {

    private ArticleFixture() {
    }

    public static final ObjectId DEFAULT_ID = new ObjectId("67d2b940d88d2b9236a1fb0e");
    public static final ObjectId NON_EXISTENT_ID = new ObjectId("66e724e50000000000db4e53");

    public static final ArticleType DEFAULT_TYPE = ArticleType.OTHER;
    public static final ArticleType SECOND_TYPE = ArticleType.TECH_AI;

    public static final String DEFAULT_TITLE = "title";
    public static final String DEFAULT_NEW_TITLE = "new title";

    public static final String DEFAULT_CONTENT = "이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 최종완료";
    public static final String DEFAULT_NEW_CONTENT = "이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 이것은 테스트용 내용입니다. 최종완료";

    public static final String DEFAULT_THUMBNAIL_URI = "";
    public static final String DEFAULT_NEW_THUMBNAIL_URI = "";

    public static final long DEFAULT_LIKES_COUNT = 0;
    public static final long DEFAULT_VIEW_COUNT = 0;
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(2025, 3, 4, 0, 0, 0);
    public static final boolean DEFAULT_DISPLAYED = true;

    public static Article getNewArticle() {
        return new Article(BlogFixture.DEFAULT_ID, DEFAULT_TYPE, DEFAULT_TITLE, DEFAULT_CONTENT, DEFAULT_THUMBNAIL_URI, DEFAULT_DISPLAYED);
    }

    public static Article getSavedArticle() {
        return new Article(DEFAULT_ID, BlogFixture.DEFAULT_ID, DEFAULT_TYPE, DEFAULT_TITLE, DEFAULT_CONTENT, DEFAULT_THUMBNAIL_URI, DEFAULT_LIKES_COUNT, DEFAULT_VIEW_COUNT, DEFAULT_DATE_TIME, DEFAULT_DATE_TIME, DEFAULT_DISPLAYED);
    }

}
