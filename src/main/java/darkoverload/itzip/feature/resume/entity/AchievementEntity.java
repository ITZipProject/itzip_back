package darkoverload.itzip.feature.resume.entity;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
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
@Table(name="achievements")
@ToString
@EqualsAndHashCode(callSuper = false)
public class AchievementEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resume_id", nullable = false, updatable = false)
    private ResumeEntity resume;

    @Column(length=50)
    private String name;

    @Column(name="organization", length=100)
    private String organization;

    @Column(name="achievement_date")
    private LocalDateTime achievementDate;

    private String content;

    public Achievement convertToDomain(){
        return Achievement.builder()
                .achievementId(this.id)
                .resume(this.resume.convertToDomain())
                .name(this.name)
                .achievementDate(this.achievementDate)
                .content(this.content)
                .build();
    }

}
