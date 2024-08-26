package darkoverload.itzip.feature.resume.dto.career;

import darkoverload.itzip.feature.resume.domain.career.CreateCareer;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCareerDto {

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

    public CreateCareer create(){
        return CreateCareer.builder()
                .companyName(this.companyName)
                .careerPosition(this.careerPosition)
                .department(this.department)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
