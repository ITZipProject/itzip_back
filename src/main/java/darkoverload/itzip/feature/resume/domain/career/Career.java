package darkoverload.itzip.feature.resume.domain.career;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.career.CareerDto;
import darkoverload.itzip.feature.resume.entity.CareerEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Career {

    // 이력서
    private Resume resume;

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

    @Builder
    public Career(Resume resume, String companyName, String careerPosition, String department, LocalDateTime startDate, LocalDateTime endDate, Long careerId) {
        this.resume = resume;
        this.companyName = companyName;
        this.careerPosition = careerPosition;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
        this.careerId = careerId;
    }

    public static Career update(CareerDto career, Resume resume) {
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
                .resume(this.resume.toEntity())
                .companyName(this.companyName)
                .careerPosition(this.careerPosition)
                .department(this.department)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .id(this.careerId)
                .build();
    }

}
