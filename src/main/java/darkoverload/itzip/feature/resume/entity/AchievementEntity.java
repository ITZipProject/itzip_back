package darkoverload.itzip.feature.resume.entity;

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
@Table(name="achievements")
public class AchievementEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;

    @Column(name="resume_id", nullable = false, insertable = false, updatable = false)
    private Long resumeId;

    @Column(length=50)
    private String name;

    @Column(name="organization", length=100)
    private String organization;

    @Column(name="achievement_date")
    private LocalDateTime achievementDate;

    @Column
    private String content;

}
