package darkoverload.itzip.feature.techinfo.dto.like;

import darkoverload.itzip.feature.techinfo.domain.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeStatusDto {
    private Long userId;
    private String postId;
    private Boolean isLiked;  // true면 좋아요 추가, false면 좋아요 취소

    public Like convertToDomain() {
        return Like.builder()
                .userId(this.userId)
                .postId(this.postId)
                .build();
    }
}