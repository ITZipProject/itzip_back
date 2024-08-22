package darkoverload.itzip.feature.resume.dto.career;

import darkoverload.itzip.feature.resume.code.CareerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CareerDto {

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
