package darkoverload.itzip.feature.resume.domain.career;

import darkoverload.itzip.feature.resume.code.CareerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Career {

    // 회사명
    private String companyName;

    // 상태
    private CareerStatus careerStatus;

    // 직책
    private String careerPosition;

    // 부서
    private String department;

    // 입사일
    private LocalDateTime startDate;

    // 퇴사일
    private LocalDateTime endDate;

}
