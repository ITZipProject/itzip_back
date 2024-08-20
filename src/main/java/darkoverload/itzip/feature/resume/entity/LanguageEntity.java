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
@Table(name="languages")
public class LanguageEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false, insertable = false)
    private Long userId;

    @Column(name="resume_id", nullable = false, insertable = false, updatable = false)
    private Long resumeId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length=50)
    private String level;

    private int score;

    @Column(name="acquisition_date", nullable = false)
    private LocalDateTime acquisitionDate;

}
