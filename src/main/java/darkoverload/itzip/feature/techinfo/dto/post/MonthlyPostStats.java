package darkoverload.itzip.feature.techinfo.dto.post;

import lombok.Getter;

import java.util.List;

/**
 * 월별 게시글 통계를 나타내는 DTO 클래스.
 * 월과 해당 월의 주별 게시글 통계 목록을 포함합니다.
 */
@Getter
public class MonthlyPostStats {

    private final int month;
    private final List<WeeklyPostStats> weeks;

    /**
     * MonthlyPostStats 생성합니다.
     *
     * @param month 월 (1-12)
     * @param weeks 해당 월의 주별 게시글 통계 목록
     */
    public MonthlyPostStats(int month, List<WeeklyPostStats> weeks) {
        this.month = month;
        this.weeks = weeks;
    }

}
