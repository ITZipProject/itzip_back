package darkoverload.itzip.feature.techinfo.model.document;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import darkoverload.itzip.global.entity.MongoAuditingFields;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * MongoDb에 저장되는 댓글 문서를 나타내는 클래스.
 * MongoAuditingFields 를 상속받아 생성 및 수정 일자를 자동으로 관리합니다.
 */
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDocument extends MongoAuditingFields {

    @Id
    @Field("_id")
    private ObjectId id;

    @Field(name = "post_id")
    private ObjectId postId;

    @Field(name = "user_id")
    private Long userId;

    @Field(name = "content")
    private String content;

    @Field(name = "is_public")
    private Boolean isPublic;

    /**
     * Comment 로부터 CommentDocument 생성합니다.
     *
     * @param comment
     * @return
     */
    public static CommentDocument from(Comment comment) {
        return CommentDocument.builder()
                .postId(new ObjectId(comment.getPostId()))
                .userId(comment.getUserId())
                .content(comment.getContent())
                .isPublic(comment.getIsPublic())
                .build();
    }

    /**
     * CommentDocument 를 Comment 로 변환합니다.
     *
     * @return Comment
     */
    public Comment toModel() {
        return Comment.builder()
                .id(this.id.toHexString())
                .postId(this.postId.toHexString())
                .userId(this.userId)
                .content(this.content)
                .isPublic(this.isPublic)
                .createDate(this.createDate)
                .build();
    }

}
