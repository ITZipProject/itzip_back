package darkoverload.itzip.feature.resume.entity;


import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="careers")
@ToString
@EqualsAndHashCode(callSuper = false)
public class CareerEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resume_id", nullable = false, updatable = false)
    private ResumeEntity resume;

    @Column(name = "company_name",length = 50, nullable = false)
    private String companyName;

    @Column(name="career_position",length = 50 ,nullable = false)
    private String careerPosition;

    @Column(length = 50)
    private String department;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;

    public Career convertToDomain(){
        return Career.builder()
                .careerId(this.id)
                .resume(this.resume.convertToDomain())
                .companyName(this.companyName)
                .careerPosition(this.careerPosition)
                .department(this.department)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }

}
