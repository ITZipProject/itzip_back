package darkoverload.itzip.feature.techinfo.model.document;

import darkoverload.itzip.feature.techinfo.domain.Comment;
import darkoverload.itzip.global.entity.MongoAuditingFields;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * MongoDB에 저장되는 댓글 정보를 나타내는 문서 클래스.
 * {@link MongoAuditingFields}를 상속받아 생성 및 수정 날짜를 자동으로 관리하며,
 * 이 클래스는 MongoDB에서 댓글 정보를 다루기 위한 필드를 포함한다.
 */
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "comments")
public class CommentDocument extends MongoAuditingFields {

    /**
     * 댓글의 고유 식별자 (MongoDB ObjectId).
     */
    @Id
    @Field("_id")
    private ObjectId id;

    /**
     * 해당 댓글이 속한 포스트의 ID (MongoDB ObjectId).
     */
    @Field(name = "post_id")
    private ObjectId postId;

    /**
     * 댓글 작성자의 ID.
     */
    @Field(name = "user_id")
    private Long userId;

    /**
     * 댓글 내용.
     */
    @Field(name = "content")
    private String content;

    /**
     * 댓글의 공개 여부.
     */
    @Field(name = "is_public")
    private Boolean isPublic;

    /**
     * CommentDocument를 PostId 없이 Comment 도메인 객체로 변환.
     *
     * @return 변환된 Comment 객체
     */
    public Comment convertToDocumentWithoutPostId() {
        return Comment.builder()
                .id(this.id.toHexString())
                .content(this.content)
                .userId(this.userId)
                .createDate(this.createDate)
                .build();
    }
}