package darkoverload.itzip.feature.techinfo.model.document;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.global.entity.MongoAuditingFields;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * MongoDB에 저장되는 게시글 문서를 나타내는 클래스.
 * MongoAuditingFields 를 상속받아 생성 및 수정 일자를 자동으로 관리합니다.
 */
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDocument extends MongoAuditingFields {

    @Id
    @Field("_id")
    private ObjectId id;

    @Field(name = "blog_id")
    private Long blogId;

    @Field(name = "category_id")
    private ObjectId categoryId;

    @Field(name = "title")
    private String title;

    @Field(name = "content")
    private String content;

    @Field(name = "view_count")
    private Integer viewCount;

    @Field(name = "like_count")
    private Integer likeCount;

    @Field(name = "is_public")
    private Boolean isPublic;

    @Field(name = "thumbnail_image_path")
    private String thumbnailImagePath;

    @Field(name = "content_image_paths")
    private List<String> contentImagePaths;

    /**
     * Post 로부터 PostDocument 를 생성합니다.
     *
     * @param post 변환할 Post
     * @return PostDocument
     */
    public static PostDocument from(Post post) {
        return PostDocument.builder()
                .id(new ObjectId(post.getId()))
                .blogId(post.getBlogId())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .isPublic(post.getIsPublic())
                .thumbnailImagePath(post.getThumbnailImagePath())
                .contentImagePaths(post.getContentImagePaths())
                .build();
    }

    /**
     * PostDocument 를 Post 로 변환합니다.
     *
     * @return Post
     */
    public Post toModel() {
        return Post.builder()
                .id(this.id.toHexString())
                .blogId(this.blogId)
                .categoryId(this.categoryId != null ? this.categoryId.toHexString() : null)
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .likeCount(this.likeCount)
                .isPublic(this.isPublic)
                .createDate(this.createDate)
                .thumbnailImagePath(this.thumbnailImagePath)
                .contentImagePaths(this.contentImagePaths)
                .build();
    }

}