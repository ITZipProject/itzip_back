package darkoverload.itzip.feature.job.domain;

import darkoverload.itzip.feature.job.entity.JobInfoEntity;
import jakarta.persistence.Column;
import lombok.*;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobInfo {

    // sequence id값
    private Long id;

    // 공고 번호
    private Long positionId;

    // 기업명
    private String companyName;

    //기업정보 URL
    private String companyHref;

    //채용정보  URL
    private String url;

    //공고 진행 여부 (1: 진행중, 0:마감)
    @Column(length = 1)
    private String active;

    //공고제목
    private String title;

    //업종 코드
    private String industryCode;

    //업종 명
    private String industryName;

    // 지역 코드
    private String locationCode;

    // 지역명
    private String locationName;

    // 근무형태 코드
    private String jobTypeCode;

    // 근무형태명
    private String jobTypeName;

    // 상위 직무 코드
    private String jobMidCode;

    // 상위 직무 명
    private String jobMidName;

    // 직무명
    private String jobName;

    // 직종 코드
    private String jobCode;

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
    private String keyword;

    // 연봉코드
    @Column(name="salary_code", length=4)
    private String salaryCode;

    // 연봉명
    @Column(name="salary_name", length=50)
    private String salaryName;

    // 채용일
    private LocalDateTime postingDate;

    // 채용마감일
    private LocalDateTime expirationDate;

    // 마감일 코드
    private String closeTypeCode;

    // 마감일 명
    private String closeTypeName;

    public JobInfoEntity toIdEntity(){
        return JobInfoEntity.builder()
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
                .requiredEducationCode(this.requiredEducationCode)
                .requiredEducationName(this.requiredEducationName)
                .keyword(this.keyword)
                .salaryCode(this.salaryCode)
                .salaryName(this.salaryName)
                .postingDate(this.postingDate)
                .expirationDate(this.expirationDate)
                .closeTypeCode(this.closeTypeCode)
                .closeTypeName(this.closeTypeName)
                .build();
    }

    public JobInfoEntity toEntity(){
        return JobInfoEntity.builder()
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
                .requiredEducationCode(this.requiredEducationCode)
                .requiredEducationName(this.requiredEducationName)
                .keyword(this.keyword)
                .salaryCode(this.salaryCode)
                .salaryName(this.salaryName)
                .postingDate(this.postingDate)
                .expirationDate(this.expirationDate)
                .closeTypeCode(this.closeTypeCode)
                .closeTypeName(this.closeTypeName)
                .build();
    }
}
