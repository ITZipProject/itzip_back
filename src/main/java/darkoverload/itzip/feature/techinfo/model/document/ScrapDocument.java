package darkoverload.itzip.feature.techinfo.model.document;

import darkoverload.itzip.global.entity.MongoAuditingFields;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "scraps")
public class ScrapDocument extends MongoAuditingFields {

    /**
     * 스크랩의 고유 식별자 (MongoDB ObjectId).
     */
    @Id
    @Field("_id")
    private ObjectId id;

    /**
     * 스크랩이 눌린 포스트의 ID (MongoDB ObjectId).
     */
    @Field("post_id")
    private ObjectId postId;

    /**
     * 스크랩를 누른 사용자의 ID.
     */
    @Field("user_id")
    private Long userId;
}