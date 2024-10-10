package darkoverload.itzip.feature.techinfo.dto.post.month;

import darkoverload.itzip.feature.techinfo.dto.post.week.WeeklyPostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 월별 포스트 통계를 나타내는 DTO 클래스.
 * 주별 포스트 통계를 리스트로 관리하며, 연도별 포스트 통계에서 사용됨.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyPostDto {

    /**
     * 해당 월을 나타내는 필드 (1월 ~ 12월).
     */
    private int month;

    /**
     * 해당 월의 주별 포스트 통계를 담고 있는 리스트.
     */
    private List<WeeklyPostDto> weeks;
}