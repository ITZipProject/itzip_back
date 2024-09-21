package darkoverload.itzip.feature.techinfo.domain;

import darkoverload.itzip.feature.techinfo.model.document.LikeDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;

/**
 * 좋아요 정보를 나타내는 도메인 클래스.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    /**
     * 좋아요를 누른 사용자의 ID.
     */
    private Long userId;

    /**
     * 좋아요가 눌린 포스트의 ID.
     */
    private String postId;

    /**
     * Like 객체를 LikeDocument로 변환 (좋아요 ID 제외).
     *
     * @return 변환된 LikeDocument 객체
     */
    public LikeDocument convertToDocumentWithoutLikeId() {
        return LikeDocument.builder()
                .userId(this.userId)
                .postId(new ObjectId(this.postId))
                .build();
    }
}