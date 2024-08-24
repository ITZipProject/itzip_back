package darkoverload.itzip.feature.resume.dto.career;

import darkoverload.itzip.feature.resume.code.CareerStatus;
import darkoverload.itzip.feature.resume.domain.career.CreateCareer;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
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

    public CreateCareer create(){
        return CreateCareer.builder()
                .companyName(this.companyName)
                .careerStatus(this.careerStatus)
                .careerPosition(this.careerPosition)
                .department(this.department)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
