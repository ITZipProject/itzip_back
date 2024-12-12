package darkoverload.itzip.feature.techinfo.domain.like;

import darkoverload.itzip.feature.techinfo.dto.like.LikeStatus;
import lombok.Builder;
import lombok.Getter;

/**
 * 기술 정보 게시글의 좋아요를 나타내는 도메인 클래스.
 * 좋아요 ID, 포스트 ID, 사용자 ID를 포함합니다.
 */
@Getter
public class Like {

    private final String id;
    private final String postId;
    private final Long userId;

    @Builder
    public Like(String id, String postId, Long userId) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
    }

    /**
     * LikeStatus 로부터 Like 생성합니다.
     *
     * @param likeStatus 좋아요 상태 정보
     * @return Like
     */
    public static Like from(LikeStatus likeStatus) {
        return Like.builder()
                .postId(likeStatus.getPostId())
                .userId(likeStatus.getUserId())
                .build();
    }

}
