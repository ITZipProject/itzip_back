package darkoverload.itzip.feature.resume.domain.career;

import darkoverload.itzip.feature.resume.code.CareerStatus;
import darkoverload.itzip.feature.resume.entity.CareerEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCareer {

    // 이력서
    private ResumeEntity resume;

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

    public CareerEntity toEntity() {
        return CareerEntity.builder()
                .resume(this.resume)
                .companyName(this.companyName)
                .careerStatus(this.careerStatus)
                .careerPosition(this.careerPosition)
                .department(this.department)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
