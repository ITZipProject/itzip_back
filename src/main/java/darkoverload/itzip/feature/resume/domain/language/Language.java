package darkoverload.itzip.feature.resume.domain.language;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.language.LanguageDto;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Language {

    // 이력서
    private Resume resume;

    // 어학시험명
    private String name;

    // 점수
    private String score;

    // 취득일
    private LocalDateTime acquisitionDate;

    // 아이디
    private Long languageId;

    @Builder
    public Language(Resume resume, String name, String score, LocalDateTime acquisitionDate, Long languageId) {
        this.resume = resume;
        this.name = name;
        this.score = score;
        this.acquisitionDate = acquisitionDate;
        this.languageId = languageId;
    }

    public static Language update(LanguageDto language, Resume resume) {
        return Language.builder()
                .resume(resume)
                .name(language.getName())
                .score(language.getScore())
                .acquisitionDate(language.getAcquisitionDate())
                .languageId(language.getLanguageId())
                .build();
    }

    public LanguageEntity toEntity() {
        return LanguageEntity.builder()
                .resume(this.resume.toEntity())
                .name(this.name)
                .score(this.score)
                .acquisitionDate(this.acquisitionDate)
                .id(this.languageId)
                .build();
    }


}
