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
@Table(name="MySkills")
public class MySkillEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false, insertable = false)
    private Long userId;

    @Column(name="resume_id", nullable = false, insertable = false, updatable = false)
    private Long resumeId;

    private String name;

}
