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

    // 점수
    private String score;

    // 취득일
    private LocalDateTime acquisitionDate;

    // 아이디
    private Long languageId;

    public LanguageDto(String name, String score, LocalDateTime acquisitionDate) {
        this.name = name;
        this.score = score;
        this.acquisitionDate = acquisitionDate;
    }

    public Language toModel(){
        return Language.builder()
                .name(this.name)
                .score(this.score)
                .acquisitionDate(this.acquisitionDate)
                .languageId(this.languageId)
                .build();
    }
}
