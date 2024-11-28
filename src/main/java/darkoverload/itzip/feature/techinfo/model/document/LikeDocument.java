package darkoverload.itzip.feature.techinfo.model.document;

import darkoverload.itzip.feature.techinfo.domain.like.Like;
import darkoverload.itzip.global.entity.MongoAuditingFields;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * MongoDB에 저장되는 좋아요 문서를 나타내는 클래스.
 * MongoAuditingFields 를 상속받아 생성 및 수정 일자를 자동으로 관리합니다.
 */
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeDocument extends MongoAuditingFields {

    @Id
    @Field("_id")
    private ObjectId id;

    @Field("post_id")
    private ObjectId postId;

    @Field("user_id")
    private Long userId;

    /**
     * Like 로부터 LikeDocument 생성합니다.
     *
     * @param like
     * @return
     */
    public static LikeDocument from(Like like) {
        return LikeDocument.builder()
                .id(new ObjectId(like.getId()))
                .postId(new ObjectId(like.getPostId()))
                .userId(like.getUserId())
                .build();
    }

    /**
     * LikeDocument 를 Like 변환합니다.
     *
     * @return Like
     */
    public Like toModel() {
        return Like.builder()
                .postId(this.postId.toHexString())
                .userId(this.userId)
                .build();
    }

}