package darkoverload.itzip.feature.job.domain.job;

import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@ToString
@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Table(name="job_infos")
public class JobInfo extends AuditingFields {

    private static final String MAP_JOB_SCARP_COUNT_KEY = "jobScrapCount:";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 공고번호
    @Column(name="position_id")
    private Long positionId;

    // 기업명
    @Column(name="company_name", length = 100)
    private String companyName;

    //채용정보  URL
    @Column(length = 5000)
    private String url;

    //공고제목
    private String title;

    //업종 명
    @Column(name="industry_name", length=50)
    private String industryName;

    // 지역 코드
    @Column(name="location_code")
    private String locationCode;

    // 지역명
    @Column(name="location_name")
    private String locationName;

    // 직무명
    @Column(name="job_name", length = 5000)
    private String jobName;

    // 경력 최소 값
    @Column(name="experience_min")
    private Long experienceMin;

    // 경력 최대 값
    @Column(name="experience_max")
    private Long experienceMax;

    // 경력명
    @Column(name="experience_name")
    private String experienceName;

    // 키워드
    @Column(length=5000)
    private String keyword;

    // 채용일
    @Column(name="posting_date")
    private LocalDateTime postingDate;

    // 채용마감일
    @Column(name="expiration_date")
    private LocalDateTime expirationDate;

    @Column(name="scrap_count")
    @ColumnDefault("0")
    @Builder.Default
    private Integer scrapCount = 0;

    public static String makeScrapCountRedisKey(Long jobInfoId) {
        StringBuilder sb = new StringBuilder();
        return sb.append(MAP_JOB_SCARP_COUNT_KEY)
                .append(jobInfoId).toString();
    }


    public int updateScrapCount(int scrapCount) {
       this.scrapCount += scrapCount;
       return this.scrapCount;
    }

}
