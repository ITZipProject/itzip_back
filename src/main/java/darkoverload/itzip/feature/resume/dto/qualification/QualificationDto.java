package darkoverload.itzip.feature.resume.dto.qualification;

import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QualificationDto {

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

    // 아이디
    private Long qualificationId;

    public QualificationDto(String organization, LocalDateTime qualificationDate, String name, int score, String level) {
        this.organization = organization;
        this.qualificationDate = qualificationDate;
        this.name = name;
        this.score = score;
        this.level = level;
    }

    public Qualification create() {
        return Qualification.builder()
                .organization(this.organization)
                .qualificationDate(this.qualificationDate)
                .name(this.name)
                .score(this.score)
                .level(this.level)
                .build();
    }
}
