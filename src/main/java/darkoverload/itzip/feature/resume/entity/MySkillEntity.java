package darkoverload.itzip.feature.resume.entity;

import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="MySkills")
@EqualsAndHashCode(callSuper = false)
@ToString
public class MySkillEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resume_id", nullable = false, updatable = false)
    private ResumeEntity resume;

    private String name;

    public MySkill convertToDomain(){
        return MySkill.builder()
                .mySkillId(this.id)
                .resume(this.resume.convertToDomain())
                .name(this.name)
                .build();
    }

}
