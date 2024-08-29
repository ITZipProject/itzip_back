package darkoverload.itzip.feature.resume.domain.language;

import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLanguage {

    // 이력서
    private ResumeEntity resume;

    // 어학시험명
    private String name;

    // 급수
    private String level;

    // 점수
    private int score;

    // 취득일
    private LocalDateTime acquisitionDate;

    public LanguageEntity toEntity() {
        return LanguageEntity.builder()
                .resume(this.resume)
                .name(this.name)
                .level(this.level)
                .score(this.score)
                .acquisitionDate(this.acquisitionDate)
                .build();
    }
}
