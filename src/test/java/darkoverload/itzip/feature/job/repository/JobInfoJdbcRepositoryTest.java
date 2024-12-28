package darkoverload.itzip.feature.job.repository;


import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.mock.JobInfoMockData;
import darkoverload.itzip.feature.job.service.connect.port.JobInfoConnectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/jobinfo/delete-jobinfo-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(value = "/sql/jobinfo/jobinfo-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
@ActiveProfiles("test")
public class JobInfoJdbcRepositoryTest {

    @Autowired
    private JobInfoConnectRepository repository;

    @Autowired
    private JobInfoJpaRepository jpaRepository;

    @Test
    void jdbc_리스트_배치_저장_테스트() {
        List<JobInfo> jobInfos = List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond);

        assertThat(repository.saveAll(jobInfos)).isEqualTo(2);
    }

    @Test
    void jdbc_리스트_배치_업데이트_테스트() {
        List<JobInfo> jobInfos = List.of(JobInfo.builder()
                .companyName("'에릭슨엘지(주)")
                .experienceMax(5L)
                .experienceMin(0L)
                .experienceName("경력5년↑")
                .expirationDate(LocalDateTime.of(2024, 9, 26, 23, 59, 59))
                .industryName("네트워크·통신·모바일")
                .jobName("backend")
                .keyword("backend")
                .locationCode("101080")
                .locationName("서울 &gt; 금천구")
                .positionId(48919419L)
                .postingDate(LocalDateTime.of(2024, 8, 30, 13, 30, 48))
                .title("spring backend")
                .url("http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48919419&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
                .build());

        repository.updateAll(jobInfos);

        JobInfo expected = JobInfo.builder()
                .id(1L)
                .companyName("'에릭슨엘지(주)")
                .experienceMax(5L)
                .experienceMin(0L)
                .experienceName("경력5년↑")
                .expirationDate(LocalDateTime.of(2024, 9, 26, 23, 59, 59))
                .industryName("네트워크·통신·모바일")
                .jobName("backend")
                .keyword("backend")
                .locationCode("101080")
                .locationName("서울 &gt; 금천구")
                .positionId(48919419L)
                .postingDate(LocalDateTime.of(2024, 8, 30, 13, 30, 48))
                .title("spring backend")
                .url("http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48919419&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
                .build();

        JobInfo result = jpaRepository.findByPositionId(48919419L);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void jdbc_리스트_배치_삭제_테스트() {
        List<Long> positionIds = List.of(48919419L);

        assertThat(repository.deleteAll(positionIds)).isEqualTo(1);
    }

}
