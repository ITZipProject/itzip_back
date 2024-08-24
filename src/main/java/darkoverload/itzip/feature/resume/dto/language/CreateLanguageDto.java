package darkoverload.itzip.feature.resume.dto.language;

import darkoverload.itzip.feature.resume.domain.language.CreateLanguage;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLanguageDto {

    // 어학시험명
    private String name;

    // 급수
    private String level;

    // 점수
    private int score;

    // 취득일
    private LocalDateTime acquisitionDate;

    public CreateLanguage create(){
        return CreateLanguage.builder()
                .name(this.name)
                .level(this.level)
                .score(this.score)
                .acquisitionDate(this.acquisitionDate)
                .build();
    }
}
