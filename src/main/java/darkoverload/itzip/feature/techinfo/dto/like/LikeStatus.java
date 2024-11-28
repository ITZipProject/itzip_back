package darkoverload.itzip.feature.techinfo.dto.like;

import lombok.Builder;
import lombok.Getter;

/**
 * 기술 정보 게시글의 좋아요 상태를 나타내는 DTO 클래스.
 * 좋아요 ID, 게시글 ID, 사용자 ID, 좋아요 여부를 포함합니다.
 */
@Getter
public class LikeStatus {

    private final String id;
    private final String postId;
    private final Long userId;
    private final Boolean isLiked;

    @Builder
    public LikeStatus(String id, String postId, Long userId, Boolean isLiked) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.isLiked = isLiked;
    }

    /**
     * 게시글 ID, 사용자 ID, 좋아요 여부로부터 LikeStatus 생성합니다.
     *
     * @param postId  게시글 ID
     * @param userId  사용자 ID
     * @param isLiked 좋아요 ID
     * @return LikeStatus
     */
    public static LikeStatus from(String postId, Long userId, Boolean isLiked) {
        return LikeStatus.builder()
                .postId(postId)
                .userId(userId)
                .isLiked(isLiked)
                .build();
    }

}
