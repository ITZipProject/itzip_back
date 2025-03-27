package darkoverload.itzip.feature.techinfo.domain.entity;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@EqualsAndHashCode
@Document(collection = "articles")
@CompoundIndex(
        name = "idx_type_displayed_created_at",
        def = "{'type': 1, 'is_displayed': 1, 'created_at': -1}"
)
public class Article {

    @Id
    private ObjectId id;

    @Field("blog_id")
    private Long blogId;

    @Field("type")
    private ArticleType type;

    @Field("title")
    private String title;

    @Field("content")
    private String content;

    @Field("thumbnail_image_uri")
    private String thumbnailImageUri;

    @Field("likes_count")
    private Long likesCount;

    @Field("view_count")
    private Long viewCount;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Field("is_displayed")
    private Boolean displayed;

    protected Article() {
    }

    public Article(final long blogId, final ArticleType type, final String title, final String content, final String thumbnailImageUri, boolean displayed) {
        this.blogId = blogId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.thumbnailImageUri = thumbnailImageUri;
        this.likesCount = 0L;
        this.viewCount = 0L;
        this.displayed = displayed;
    }

    public Article(final ObjectId id, final long blogId, final ArticleType type, final String title, final String content, final String thumbnailImageUri, final long likesCount, final long viewCount, final LocalDateTime createdAt, final boolean displayed) {
        this.id = id;
        this.blogId = blogId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.thumbnailImageUri = thumbnailImageUri;
        this.likesCount = likesCount;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.displayed = displayed;
    }

    public Article(final ObjectId id, final long blogId, final ArticleType type, final String title, final String content, final String thumbnailImageUri, final long likesCount, final long viewCount, final LocalDateTime createdAt, final LocalDateTime updatedAt, final boolean displayed) {
        this.id = id;
        this.blogId = blogId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.thumbnailImageUri = thumbnailImageUri;
        this.likesCount = likesCount;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.displayed = displayed;
    }

    public static Article create(final long blogId, final String type, final String title, final String content, final String thumbnailImageUri) {
        checkTitle(title);
        return new Article(blogId, ArticleType.from(type), title, content, thumbnailImageUri, true);
    }

    private static void checkTitle(final String title) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new RestApiException(CommonExceptionCode.ARTICLE_TITLE_REQUIRED);
        }
    }

    public Article update(final String type, final String title, final String content, final String thumbnailImageUri) {
        checkTitle(title);
        return new Article(this.id, this.blogId, ArticleType.from(type), title, content, thumbnailImageUri, this.likesCount, this.viewCount, this.createdAt, this.displayed);
    }

    public Article hide() {
        this.displayed = false;
        return this;
    }

}
