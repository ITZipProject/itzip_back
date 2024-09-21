package darkoverload.itzip.feature.techinfo.dto.like;

import darkoverload.itzip.feature.techinfo.domain.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Like 상태를 나타내는 DTO (Data Transfer Object) 클래스.
 * 특정 유저가 특정 포스트에 대해 좋아요를 눌렀는지 여부를 관리함.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeStatusDto {

    /**
     * 유저 ID (좋아요를 누른 유저).
     */
    private Long userId;

    /**
     * 포스트 ID (좋아요가 눌린 포스트).
     */
    private String postId;

    /**
     * 좋아요 상태 (true면 좋아요 추가, false면 좋아요 취소).
     */
    private Boolean isLiked;

    /**
     * LikeStatusDto 객체를 Like 도메인 객체로 변환.
     *
     * @return 변환된 Like 도메인 객체
     */
    public Like convertToDomain() {
        return Like.builder()
                .userId(this.userId)
                .postId(this.postId)
                .build();
    }
}