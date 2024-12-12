package darkoverload.itzip.feature.techinfo.domain.scrap;

import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatus;
import lombok.Builder;
import lombok.Getter;

/**
 * 기술 정보 게시글의 스크랩을 나타내는 도메인 클래스.
 * 스크랩 ID, 게시글 ID, 사용자 ID 를 포함합니다.
 */
@Getter
public class Scrap {

    private final String id;
    private final String postId;
    private final Long userId;

    @Builder
    public Scrap(String id, String postId, Long userId) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
    }

    /**
     * ScrapStatus 로부터 Scrap 생성합니다.
     *
     * @param scrapStatus 스크랩 상태 정보
     * @return Scrap
     */
    public static Scrap from(ScrapStatus scrapStatus) {
        return Scrap.builder()
                .postId(scrapStatus.getPostId())
                .userId(scrapStatus.getUserId())
                .build();
    }

}
