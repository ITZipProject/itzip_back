package darkoverload.itzip.feature.resume.entity;

import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="languages")
@ToString
@EqualsAndHashCode(callSuper = false)
public class LanguageEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resume_id", nullable = false, updatable = false)
    private ResumeEntity resume;

    @Column(length = 50, nullable = false)
    private String name;

    private String score;

    @Column(name="acquisition_date", nullable = false)
    private LocalDateTime acquisitionDate;


    public Language convertToDomain(){
        return Language.builder()
                .languageId(this.id)
                .resume(this.resume.convertToDomain())
                .name(this.name)
                .score(this.score)
                .acquisitionDate(this.acquisitionDate)
                .build();
    }
}
