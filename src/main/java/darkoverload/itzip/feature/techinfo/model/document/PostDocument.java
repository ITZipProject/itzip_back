package darkoverload.itzip.feature.techinfo.model.document;

import darkoverload.itzip.feature.techinfo.domain.Post;
import darkoverload.itzip.global.entity.MongoAuditingFields;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * MongoDB에 저장되는 포스트 정보를 나타내는 문서 클래스.
 * {@link MongoAuditingFields}를 상속받아 생성 및 수정 날짜를 자동으로 관리하며,
 * 이 클래스는 MongoDB에서 포스트 정보를 다루기 위한 필드를 포함한다.
 */
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "posts")
public class PostDocument extends MongoAuditingFields {

    /**
     * 포스트의 고유 식별자 (MongoDB ObjectId).
     */
    @Id
    @Field("_id")
    private ObjectId id;

    /**
     * 블로그 ID (PostgreSQL의 블로그 ID와 연동).
     */
    @Field(name = "blog_id")
    private Long blogId;

    /**
     * 카테고리 ID (MongoDB ObjectId).
     */
    @Field(name = "category_id")
    private ObjectId categoryId;

    /**
     * 포스트 제목.
     */
    @Field(name = "title")
    private String title;

    /**
     * 포스트 본문.
     */
    @Field(name = "content")
    private String content;

    /**
     * 포스트 조회수.
     */
    @Field(name = "view_count")
    private Integer viewCount;

    /**
     * 포스트 좋아요 개수.
     */
    @Field(name = "like_count")
    private Integer likeCount;

    /**
     * 포스트 공개 여부.
     */
    @Field(name = "is_public")
    private Boolean isPublic;

    /**
     * 썸네일 이미지 경로.
     */
    @Field(name = "thumbnail_image_path")
    private String thumbnailImagePath;

    /**
     * 본문에 사용된 이미지 경로 리스트.
     */
    @Field(name = "content_image_paths")
    private List<String> contentImagePaths;

    /**
     * PostDocument를 Post 도메인 객체로 변환.
     *
     * @return 변환된 Post 객체
     */
    public Post convertToDomain() {
        return Post.builder()
                .id(this.id.toHexString())
                .blogId(this.blogId)
                .categoryId(this.categoryId.toHexString())
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .likeCount(this.likeCount)
                .createDate(this.createDate)
                .thumbnailImagePath(this.thumbnailImagePath)
                .contentImagePaths(this.contentImagePaths)
                .build();
    }

    /**
     * PostDocument를 BlogId와 ViewCount 없이 Post 도메인 객체로 변환.
     *
     * @return 변환된 Post 객체
     */
    public Post convertToDomainWithoutBlodIdAndViewCount() {
        return Post.builder()
                .id(this.id.toHexString())
                .categoryId(this.categoryId.toHexString())
                .title(this.title)
                .content(this.content)
                .likeCount(this.likeCount != null ? this.likeCount : 0)
                .createDate(this.createDate)
                .thumbnailImagePath(this.thumbnailImagePath)
                .build();
    }

    /**
     * PostDocument를 ViewCount 없이 Post 도메인 객체로 변환.
     *
     * @return 변환된 Post 객체
     */
    public Post convertToDomainWithoutViewCount() {
        return Post.builder()
                .id(this.id.toHexString())
                .blogId(this.blogId)
                .categoryId(this.categoryId.toHexString())
                .title(this.title)
                .content(this.content)
                .likeCount(this.likeCount != null ? this.likeCount : 0)
                .createDate(this.createDate)
                .thumbnailImagePath(this.thumbnailImagePath)
                .build();
    }

    /**
     * PostDocument를 기본 필드(id, title, createDate)만으로 Post 도메인 객체로 변환.
     *
     * @return 변환된 Post 객체
     */
    public Post convertToDomainWithBasicFields() {
        return Post.builder()
                .id(this.id.toHexString())
                .title(this.title)
                .createDate(this.createDate)
                .build();
    }
}