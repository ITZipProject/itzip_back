package darkoverload.itzip.feature.techinfo.dto.post;

import lombok.Getter;

import java.util.List;

/**
 * 연간 게시글 통계를 나타내는 DTO 클래스.
 * 년도와 해당 년도의 월별 게시글 통계 목록을 포함합니다.
 */
@Getter
public class YearlyPostStats {

    private final int year;
    private final List<MonthlyPostStats> months;

    /**
     * YearlyPostStats 생성합니다.
     *
     * @param year   년도
     * @param months 해당 년도의 월별 게시글 통계 목록
     */
    public YearlyPostStats(int year, List<MonthlyPostStats> months) {
        this.year = year;
        this.months = months;
    }

}
