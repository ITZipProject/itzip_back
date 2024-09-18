package darkoverload.itzip.feature.resume.domain.career;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.career.CareerDto;
import darkoverload.itzip.feature.resume.entity.CareerEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Career {

    // 이력서
    private ResumeEntity resume;

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

    // 커리어 아이디
    private Long careerId;

    public static Career update(CareerDto career, ResumeEntity resume) {
        return Career.builder()
                .resume(resume)
                .careerId(career.getCareerId())
                .companyName(career.getCompanyName())
                .careerPosition(career.getCareerPosition())
                .department(career.getDepartment())
                .startDate(career.getStartDate())
                .endDate(career.getEndDate())
                .build();
    }

    public CareerEntity toEntity() {
        return CareerEntity.builder()
                .resume(this.resume)
                .companyName(this.companyName)
                .careerPosition(this.careerPosition)
                .department(this.department)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .id(this.careerId)
                .build();
    }
}
