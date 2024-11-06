package darkoverload.itzip.feature.resume.domain.qualification;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.qualification.QualificationDto;
import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Qualification {

    // 이력서
    private Resume resume;

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

    @Builder
    public Qualification(Resume resume, String organization, LocalDateTime qualificationDate, String name, int score, String level, Long qualificationId) {
        this.resume = resume;
        this.organization = organization;
        this.qualificationDate = qualificationDate;
        this.name = name;
        this.score = score;
        this.level = level;
        this.qualificationId = qualificationId;
    }

    public static Qualification update(QualificationDto qualification, Resume resume) {

        return Qualification.builder()
                .resume(resume)
                .organization(qualification.getOrganization())
                .qualificationDate(qualification.getQualificationDate())
                .name(qualification.getName())
                .score(qualification.getScore())
                .level(qualification.getLevel())
                .qualificationId(qualification.getQualificationId())
                .build();
    }

    public QualificationEntity toEntity() {
        return QualificationEntity.builder()
                .resume(this.resume.toEntity())
                .organization(this.organization)
                .qualificationDate(this.qualificationDate)
                .name(this.name)
                .score(this.score)
                .level(this.level)
                .id(this.qualificationId)
                .build();
    }
}
