package darkoverload.itzip.feature.techinfo.dto.post;

import lombok.Getter;

/**
 * 주별 게시글 통계를 나타내는 DTO 클래스.
 * 주차와 해당 주의 게시글 개수를 포함합니다.
 */
@Getter
public class WeeklyPostStats {

    private int week;
    private int postCount;

    /**
     * WeeklyPostStats 생성합니다.
     *
     * @param week      주차 (1-5)
     * @param postCount 해당 주의 게시글 개수
     */
    public WeeklyPostStats(int week, int postCount) {
        this.week = week;
        this.postCount = postCount;
    }

}
