package darkoverload.itzip.feature.techinfo.dto.scrap;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScrapStatus {

    private final String id;
    private final String postId;
    private final Long userId;
    private final Boolean isScrapped;

    @Builder
    public ScrapStatus(String id, String postId, Long userId, Boolean isScrapped) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.isScrapped = isScrapped;
    }

    public static ScrapStatus from(String postId, Long userId, Boolean isScrapped) {
        return ScrapStatus.builder()
                .postId(postId)
                .userId(userId)
                .isScrapped(isScrapped)
                .build();
    }

}
