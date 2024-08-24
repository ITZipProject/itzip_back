package darkoverload.itzip.feature.resume.dto.education;

import darkoverload.itzip.feature.resume.domain.education.CreateEducation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEducationDto {

    // 학교명
    private String schoolName;

    // 전공
    private String major;

    // 입학일
    private LocalDateTime startDate;

    // 졸업일
    private LocalDateTime endDate;


    public CreateEducation create(){
        return CreateEducation.builder()
                .schoolName(this.schoolName)
                .major(this.major)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
