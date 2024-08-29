package darkoverload.itzip.feature.resume.domain.education;


import darkoverload.itzip.feature.resume.entity.EducationEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEducation {

    // 이력서
    private ResumeEntity resume;

    // 학교명
    private String schoolName;

    // 전공
    private String major;

    // 입학일
    private LocalDateTime startDate;

    // 졸업일
    private LocalDateTime endDate;

    public EducationEntity toEntity() {
        return EducationEntity.builder()
                .resume(resume)
                .schoolName(this.schoolName)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .major(this.major)
                .build();
    }
}
