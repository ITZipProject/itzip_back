package darkoverload.itzip.feature.resume.entity;


import darkoverload.itzip.feature.resume.code.CareerStatus;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="careers")
public class CareerEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;

    @Column(name="resume_id", nullable = false, insertable = false, updatable = false)
    private Long resumeId;

    @Column(name = "company_name",length = 50, nullable = false)
    private String companyName;

    @Column(name= "career_status", nullable = false)
    private CareerStatus careerStatus;

    @Column(name="career_position",length = 50 ,nullable = false)
    private String careerPosition;

    @Column(length = 50)
    private String department;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;

}
