package darkoverload.itzip.feature.resume.dto.language;

import darkoverload.itzip.feature.resume.domain.language.Language;
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
public class LanguageDto {

    // 어학시험명
    @Schema(description = "언어 이름", example = "English")
    private String name;

    // 점수
    @Schema(description = "점수", example = "TOEIC 900")
    private String score;

    // 취득일
    @Schema(description = "취득 날짜", example = "2022-10-01T00:00:00.000Z")
    private LocalDateTime acquisitionDate;

    // 아이디
    @Schema(description = "언어 아이디 값", example = "이력서 생성 시 제외 업데이트 시 추가해서 사용")
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
