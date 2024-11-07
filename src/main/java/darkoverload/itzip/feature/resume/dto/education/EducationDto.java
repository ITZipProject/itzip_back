package darkoverload.itzip.feature.resume.dto.education;

import darkoverload.itzip.feature.resume.domain.education.Education;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EducationDto {

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

    public EducationDto(String schoolName, String major, LocalDateTime startDate, LocalDateTime endDate) {
        this.schoolName = schoolName;
        this.major = major;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Education toModel(){
        return Education.builder()
                .educationId(this.educationId)
                .schoolName(this.schoolName)
                .major(this.major)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
