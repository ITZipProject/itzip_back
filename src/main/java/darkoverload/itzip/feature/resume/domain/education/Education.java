package darkoverload.itzip.feature.resume.domain.education;


import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.education.EducationDto;
import darkoverload.itzip.feature.resume.entity.EducationEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Education {

    // 이력서
    private Resume resume;

    // 학교명
    private String schoolName;

    // 전공
    private String major;

    // 입학일
    private LocalDateTime startDate;

    // 졸업일
    private LocalDateTime endDate;

    // 아이디
    private Long educationId;

    public static Education update(EducationDto education, Resume resume){
        return Education.builder()
                .resume(resume)
                .schoolName(education.getSchoolName())
                .major(education.getMajor())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .educationId(education.getEducationId())
                .build();
    }

    public EducationEntity toEntity() {
        return EducationEntity.builder()
                .resume(this.resume.toEntity())
                .schoolName(this.schoolName)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .major(this.major)
                .id(this.educationId)
                .build();
    }
}
