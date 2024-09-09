package darkoverload.itzip.feature.job.entity;


import darkoverload.itzip.feature.job.domain.JobInfoScrap;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@ToString
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Builder
@Table(name="job_info_scraps")
@EntityListeners(AuditingEntityListener.class)
public class JobInfoScrapEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name="job_id")
    private JobInfoEntity jobInfo;

    public JobInfoScrap convertToDomain() {
        return JobInfoScrap.builder()
                .user(user.convertToDomain())
                .jobInfo(jobInfo.convertToDomain())
                .build();
    }
}
