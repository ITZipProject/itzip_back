package darkoverload.itzip.feature.resume.entity;

import darkoverload.itzip.feature.resume.domain.activity.Activity;
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
@Table(name="activities")
@EqualsAndHashCode(callSuper = false)
@ToString
public class ActivityEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resume_id",nullable = false, updatable = false)
    private ResumeEntity resume;

    private String name;

    private String content;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;


    public Activity convertToDomain() {
        return Activity.builder()
                .activityId(this.id)
                .resume(this.resume.convertToDomain())
                .name(this.name)
                .content(this.content)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
