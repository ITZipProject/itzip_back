package darkoverload.itzip.feature.resume.domain.language;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.language.LanguageDto;
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
public class Language {

    // 이력서
    private Resume resume;

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

    public static Language update(LanguageDto language, Resume resume) {
        return Language.builder()
                .resume(resume)
                .name(language.getName())
                .level(language.getLevel())
                .score(language.getScore())
                .acquisitionDate(language.getAcquisitionDate())
                .languageId(language.getLanguageId())
                .build();
    }

    public LanguageEntity toEntity() {
        return LanguageEntity.builder()
                .resume(this.resume.toEntity())
                .name(this.name)
                .level(this.level)
                .score(this.score)
                .acquisitionDate(this.acquisitionDate)
                .id(this.languageId)
                .build();
    }
}
