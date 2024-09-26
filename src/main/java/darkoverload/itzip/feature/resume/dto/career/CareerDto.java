package darkoverload.itzip.feature.resume.dto.career;

import darkoverload.itzip.feature.resume.domain.career.Career;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CareerDto {

    // 회사명
    private String companyName;

    // 직책
    private String careerPosition;

    // 부서
    private String department;

    // 입사일
    private LocalDateTime startDate;

    // 퇴사일
    private LocalDateTime endDate;

    // 아이디
    private Long careerId;

    public Career create(){
        return Career.builder()
                .companyName(this.companyName)
                .careerPosition(this.careerPosition)
                .department(this.department)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }

    public Career update() {
        return Career.builder()
                .companyName(this.companyName)
                .careerPosition(this.careerPosition)
                .department(this.department)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .careerId(this.careerId)
                .build();
    }
}
