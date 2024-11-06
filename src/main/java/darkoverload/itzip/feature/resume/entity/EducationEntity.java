package darkoverload.itzip.feature.resume.entity;

import darkoverload.itzip.feature.resume.domain.education.Education;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="educations")
public class EducationEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resume_id", nullable = false, updatable = false)
    private ResumeEntity resume;

    @Column(name="school_name")
    private String schoolName;

    @Column(length=50)
    private String major;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;

    public Education convertToDomain(){
        return Education.builder()
                .educationId(this.id)
                .resume(this.resume.convertToDomain())
                .schoolName(this.schoolName)
                .major(this.major)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }

}
