package darkoverload.itzip.feature.job.domain.scrap;


import darkoverload.itzip.feature.job.domain.job.JobInfo;
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
@Table(
        name="job_info_scraps",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "job_id"})
)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
public class JobInfoScrap extends AuditingFields {
    public static final String MAP_JOB_SCRAP_KEY = "JOB_SCRAP:";
    private static final String DELIMITER = ":";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="job_id")
    private JobInfo jobInfo;

    public static JobInfoScrap createScrap(UserEntity user, JobInfo jobinfo) {
        return JobInfoScrap.builder()
                .user(user)
                .jobInfo(jobinfo)
                .build();
    }

    public static String makeRedisKey(Long jobInfoId, String userEmail) {
        StringBuilder sb = new StringBuilder();
        return sb.append(MAP_JOB_SCRAP_KEY)
                .append(jobInfoId)
                .append(DELIMITER)
                .append(userEmail).toString();
    }

    public static String[] getRedisKeyParts(String redisKey) {
        return redisKey.split(DELIMITER);
    }

}
