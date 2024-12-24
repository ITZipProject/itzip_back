package darkoverload.itzip.feature.resume.domain.resume.scrap;

import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name="resume_scraps",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "resume_id"})
)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
public class ResumeScrap extends AuditingFields {
    public static final String MAP_RESUME_SCRAP_KEY = "RESUME_SCRAP:";
    private static final String DELIMITER = ":";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="resume_id")
    private ResumeEntity resume;

    @Builder
    public ResumeScrap(Long id, UserEntity user, ResumeEntity resume) {
        this.id = id;
        this.user = user;
        this.resume = resume;
    }

    public static ResumeScrap createScrap(UserEntity user, ResumeEntity resume) {
        return ResumeScrap.builder()
                .user(user)
                .resume(resume)
                .build();
    }

    public static String makeRedisKey(Long jobInfoId, String userEmail) {
        StringBuilder sb = new StringBuilder();
        return sb.append(MAP_RESUME_SCRAP_KEY)
                .append(jobInfoId)
                .append(DELIMITER)
                .append(userEmail).toString();
    }

    public static String[] getRedisKeyParts(String redisKey) {
        return redisKey.split(DELIMITER);
    }

}
