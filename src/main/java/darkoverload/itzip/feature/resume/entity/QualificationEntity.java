package darkoverload.itzip.feature.resume.entity;


import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="qualifications")
public class QualificationEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resume_id", nullable = false, updatable = false)
    private ResumeEntity resume;

    @Column(length=50)
    private String name;

    private int score;

    @Column(length=100)
    private String organization;

    @Column(name="qualification_date", nullable = false)
    private LocalDateTime qualificationDate;

    @Column(length=5)
    private String level;

    public Qualification convertToDomain(){
        return Qualification.builder()
                .qualificationId(this.id)
                .resume(this.resume.convertToDomain())
                .name(this.name)
                .score(this.score)
                .organization(this.organization)
                .qualificationDate(this.qualificationDate)
                .level(this.level)
                .build();
    }

}
