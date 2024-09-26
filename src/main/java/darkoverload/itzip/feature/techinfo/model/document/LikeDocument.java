package darkoverload.itzip.feature.techinfo.model.document;

import darkoverload.itzip.feature.techinfo.domain.Like;
import darkoverload.itzip.global.entity.MongoAuditingFields;

import org.bson.types.ObjectId;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * MongoDB에 저장되는 좋아요 정보를 나타내는 문서 클래스.
 * {@link MongoAuditingFields}를 상속받아 생성 및 수정 날짜를 자동으로 관리하며,
 * 이 클래스는 MongoDB에서 좋아요 정보를 다루기 위한 필드를 포함한다.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "likes")
public class LikeDocument extends MongoAuditingFields {

    /**
     * 좋아요의 고유 식별자 (MongoDB ObjectId).
     */
    @Id
    @Field("_id")
    private ObjectId id;

    /**
     * 좋아요가 눌린 포스트의 ID (MongoDB ObjectId).
     */
    @Field("post_id")
    private ObjectId postId;

    /**
     * 좋아요를 누른 사용자의 ID.
     */
    @Field("user_id")
    private Long userId;

    /**
     * LikeDocument를 Like 도메인 객체로 변환.
     *
     * @return 변환된 Like 객체
     */
    public Like convertToDomain() {
        return Like.builder()
                .userId(this.userId)
                .postId(this.postId.toHexString())
                .build();
    }
}