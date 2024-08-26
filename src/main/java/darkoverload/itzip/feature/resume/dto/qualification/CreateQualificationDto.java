package darkoverload.itzip.feature.resume.dto.qualification;

import darkoverload.itzip.feature.resume.domain.qualification.CreateQualification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateQualificationDto {

    // 발급기관
    private String organization;

    // 취득일
    private LocalDateTime qualificationDate;

    // 자격증명
    private String name;

    // 점수
    private int score;

    // 급
    private String level;

    public CreateQualification create() {
        return CreateQualification.builder()
                .organization(this.organization)
                .qualificationDate(this.qualificationDate)
                .name(this.name)
                .score(this.score)
                .level(this.level)
                .build();
    }
}
