package darkoverload.itzip.feature.resume.dto.language;

import darkoverload.itzip.feature.resume.domain.language.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto {

    // 어학시험명
    private String name;

    // 급수
    private String level;

    // 점수
    private int score;

    // 취득일
    private LocalDateTime acquisitionDate;

    // 아이디
    private Long languageId;

    public LanguageDto(String name, String level, int score, LocalDateTime acquisitionDate) {
        this.name = name;
        this.level = level;
        this.score = score;
        this.acquisitionDate = acquisitionDate;
    }

    public Language create(){
        return Language.builder()
                .name(this.name)
                .level(this.level)
                .score(this.score)
                .acquisitionDate(this.acquisitionDate)
                .languageId(this.languageId)
                .build();
    }
}
