package darkoverload.itzip.feature.techinfo.dto.post.week;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주별 포스트 통계를 나타내는 DTO 클래스.
 * 주차와 해당 주의 포스트 수를 관리함.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyPostDto {

    /**
     * 해당 주차를 나타내는 필드 (1주차, 2주차 등).
     */
    private int week;

    /**
     * 해당 주차의 포스트 개수를 나타내는 필드.
     */
    private int postCount;
}