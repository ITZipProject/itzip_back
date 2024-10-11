package darkoverload.itzip.feature.techinfo.dto.post.year;

import darkoverload.itzip.feature.techinfo.dto.post.month.MonthlyPostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 연도별 포스트 통계를 나타내는 DTO 클래스.
 * 월별 포스트 통계를 리스트로 관리하며, 전체 연도의 포스트 데이터를 포함함.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YearlyPostDto {

    /**
     * 해당 연도를 나타내는 필드 (예: 2024).
     */
    private int year;

    /**
     * 해당 연도의 월별 포스트 통계를 담고 있는 리스트.
     */
    private List<MonthlyPostDto> months;
}