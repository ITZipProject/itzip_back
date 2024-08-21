package darkoverload.itzip.feature.resume.entity;


import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="qualifications")
public class QualificationEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="resume_id", nullable = false, insertable = false, updatable = false)
    private Long resumeId;

    @Column(name="organization", length=100)
    private String organization;

    @Column(length=50)
    private String name;

    private int score;

    @Column(length=5)
    private String level;
}
