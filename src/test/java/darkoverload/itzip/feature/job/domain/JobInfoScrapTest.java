package darkoverload.itzip.feature.job.domain;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import darkoverload.itzip.feature.user.entity.Authority;
import darkoverload.itzip.feature.user.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class JobInfoScrapTest {

    private long jobInfoId = 13L;
    private String userEmail = "test@gmail.com";

    @Test
    void 취업_정보_스크랩_카운터_레디스_키_테스트() {
        String redisKey = JobInfoScrap.makeRedisKey(jobInfoId, userEmail);
        assertThat(redisKey).isEqualTo("JOB_SCRAP:13:test@gmail.com");
    }

    @Test
    void 취업_정보_스크랩_레디스_키_배열_생성_테스트() {
        assertThat(JobInfoScrap.redisKeyParts("JOB_SCRAP:13:test@gmail.com")).containsExactly("JOB_SCRAP", "13", "test@gmail.com");
    }

    @Test
    void 취업_정보_스크랩_정적_메서드_create_메서드_테스트() {
        UserEntity user = UserEntity.builder()
                .id(13L)
                .email("test@gmail.com")
                .nickname("test")
                .password("tttt1234@@")
                .imageUrl("https://itzip.co.kr")
                .authority(Authority.USER)
                .build();

        JobInfo jobInfo = JobInfo.builder()
                .id(11L)
                .positionId(143321L)
                .companyName("잇집회사")
                .companyHref("https://itzip.co.kr")
                .url("https://saramin.co.kr")
                .active("1")
                .title("잇집 풀스택 개발자 채용")
                .industryCode("200")
                .industryName("웹 개발")
                .locationCode("1001")
                .locationName("서울")
                .jobTypeCode("101")
                .jobTypeName("잇집")
                .jobMidCode("201")
                .jobMidName("프론트")
                .jobName("풀스택")
                .jobCode("01")
                .experienceCode("1")
                .experienceMin(0L)
                .experienceMax(0L)
                .experienceName("신입")
                .requiredEducationCode("213")
                .requiredEducationName("대졸(4년제)")
                .keyword("개발")
                .salaryCode("30,000,000")
                .salaryName("3천만원이상")
                .postingDate(LocalDateTime.of(2024, 12, 3, 23, 59, 59))
                .expirationDate(LocalDateTime.of(2025, 2, 3, 23, 59, 59))
                .closeTypeCode("2334")
                .closeTypeName("2달뒤")
                .scrapCount(0)
                .build();

        assertThat(JobInfoScrap.createScrap(user, jobInfo)).isEqualTo(JobInfoScrap.builder()
                .user(user)
                .jobInfo(jobInfo)
                .build());
    }

}
