package darkoverload.itzip.feature.job.entity;

import darkoverload.itzip.feature.job.domain.JobInfo;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Table(name="job_infos")
public class JobInfoEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 공고번호
    @Column(name="position_id")
    private Long positionId;

    // 기업명
    @Column(name="company_name", length = 100)
    private String companyName;

    //기업정보 URL
    @Column(name="company_href", length = 5000)
    private String companyHref;

    //채용정보  URL
    @Column(length = 5000)
    private String url;

    //공고 진행 여부 (1: 진행중, 0:마감)
    @Column(length = 1)
    private String active;

    //공고제목
    private String title;

    //업종 코드
    @Column(name="industry_code", length=2)
    private String industryCode;

    //업종 명
    @Column(name="industry_name", length=50)
    private String industryName;

    // 지역 코드
    @ElementCollection
    @Column(name="location_code", length=7)
    private List<String> locationCode;

    // 지역명
    @ElementCollection
    @Column(name="location_name", length=50)
    private List<String> locationName;

    // 근무형태 코드
    @ElementCollection
    @Column(name="job_type_code", length=2)
    private List<String> jobTypeCode;

    // 근무형태명
    @ElementCollection
    @Column(name="job_type_name", length=50)
    private List<String> jobTypeName;

    // 상위 직무 코드
    @ElementCollection
    @Column(name="job_mid_code", length=2)
    private List<String> jobMidCode;

    // 상위 직무 명
    @ElementCollection
    @Column(name="job_mid_name", length=50)
    private List<String> jobMidName;

    // 직무명
    @ElementCollection
    private List<String> jobName;

    // 직종 코드
    @ElementCollection
    @Column(name="job_code", length=50)
    private List<String> jobCode;

    // 경력 코드 (1: 신입, 2: 경력, 3: 신입/경력, 0: 경력무관)
    @Column(name="experience_code", length=2)
    private String experienceCode;

    // 경력 최소 값
    @Column(name="experience_min")
    private Long experienceMin;

    // 경력 최대 값
    @Column(name="experience_max")
    private Long experienceMax;

    // 경력명
    @Column(name="experience_name")
    private String experienceName;

    // 학력 코드 (표 참고)
    @Column(name="required_education_code", length=2)
    private String requiredEducationCode;

    // 학력명
    @Column(name="required_education_name", length=50)
    private String requiredEducationName;

    // 키워드
    @ElementCollection
    private List<String> keyword;

    // 지원자수
    @Column(name="apply_cnt")
    private Long applyCnt;

    // 조회수
    @Column(name="read_cnt")
    private Long readCnt;

    // 연봉코드
    @Column(name="salary_code", length=4)
    private String salaryCode;

    // 연봉명
    @Column(name="salary_name", length=50)
    private String salaryName;

    // 채용일
    @Column(name="posting_date")
    private LocalDateTime postingDate;

    // 채용마감일
    @Column(name="expiration_date")
    private LocalDateTime expirationDate;

    // 마감일 코드
    @Column(name="close_type_code", length=2)
    private String closeTypeCode;

    // 마감일 명
    @Column(name="close_type_name", length=50)
    private String closeTypeName;

    public JobInfo toDomain() {
        return JobInfo.builder()
                .id(this.id)
                .positionId(this.positionId)
                .companyName(this.companyName)
                .companyHref(this.companyHref)
                .url(this.url)
                .active(this.active)
                .title(this.title)
                .industryCode(this.industryCode)
                .industryName(this.industryName)
                .locationCode(this.locationCode)
                .locationName(this.locationName)
                .jobTypeCode(this.jobTypeCode)
                .jobTypeName(this.jobTypeName)
                .jobMidCode(this.jobMidCode)
                .jobMidName(this.jobMidName)
                .jobName(this.jobName)
                .jobCode(this.jobCode)
                .experienceCode(this.experienceCode)
                .experienceMin(this.experienceMin)
                .experienceMax(this.experienceMax)
                .experienceName(this.experienceName)
                .requiredEducationName(this.requiredEducationName)
                .requiredEducationCode(this.requiredEducationCode)
                .keyword(this.keyword)
                .applyCnt(this.applyCnt)
                .readCnt(this.readCnt)
                .salaryCode(this.salaryCode)
                .salaryName(this.salaryName)
                .postingDate(this.postingDate)
                .expirationDate(this.expirationDate)
                .closeTypeCode(this.closeTypeCode)
                .closeTypeName(this.closeTypeName)
                .build();
    }
}
