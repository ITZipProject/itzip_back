package darkoverload.itzip.feature.resume.domain.qualification;

import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateQualification {

    // 이력서
    private ResumeEntity resume;

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

    public QualificationEntity toEntity() {
        return QualificationEntity.builder()
                .resume(this.resume)
                .organization(this.organization)
                .qualificationDate(this.qualificationDate)
                .name(this.name)
                .score(this.score)
                .level(this.level)
                .build();
    }
}
