package darkoverload.itzip.feature.techinfo.model.document;

import darkoverload.itzip.feature.techinfo.domain.scrap.Scrap;
import darkoverload.itzip.global.entity.MongoAuditingFields;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * MongoDB에 저장되는 스크랩 문서를 나타내는 클래스.
 * MongoAuditingFields 를 상속받아 생성 및 수정 일자를 자동으로 관리한다.
 */
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "scraps")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapDocument extends MongoAuditingFields {

    @Id
    @Field("_id")
    private ObjectId id;

    @Field("post_id")
    private ObjectId postId;

    @Field("user_id")
    private Long userId;

    /**
     * Scrap 로부터 ScrapDocument 를 생성합니다.
     *
     * @param scrap 변환할 Scrap
     * @return ScrapDocument
     */
    public static ScrapDocument from(Scrap scrap) {
        return ScrapDocument.builder()
                .id(scrap.getId() != null ? new ObjectId(scrap.getId()) : null)
                .postId(new ObjectId(scrap.getPostId()))
                .userId(scrap.getUserId())
                .build();
    }

    /**
     * ScrapDocument 를 Scrap 로 변환합니다.
     *
     * @return Scrap
     */
    public Scrap toModel() {
        return Scrap.builder()
                .postId(this.postId.toHexString())
                .userId(this.userId)
                .build();
    }

}
