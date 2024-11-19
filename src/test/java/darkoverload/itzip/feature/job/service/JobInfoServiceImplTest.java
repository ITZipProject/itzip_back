package darkoverload.itzip.feature.job.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import darkoverload.itzip.feature.job.controller.request.JobInfoScrapRequest;
import darkoverload.itzip.feature.job.entity.JobInfoScrapEntity;
import darkoverload.itzip.feature.job.repository.JobInfoScrapRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

@SqlGroup({
        @Sql(value = "/sql/jobinfo/scrap/infoscrap-repository-date.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/jobinfo/scrap/delete-infoscrap-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class JobInfoServiceImplTest {

    @Autowired
    private JobInfoService jobInfoService;

    @Autowired
    private JobInfoScrapRepository jobInfoScrapRepository;

    @Test
    void 채용정보_스크랩() {

        // given
        JobInfoScrapRequest request = new JobInfoScrapRequest(13L, "test@test.com");

        // when
        String response = jobInfoService.jobInfoScrap(request);
        JobInfoScrapEntity jobInfo = jobInfoScrapRepository.findByJobInfoId(13L, "test@test.com").get();

        // then
        assertAll(
                () -> assertEquals(response, "채용정보 스크랩을 하였습니다."),
                () -> assertEquals(jobInfo.getJobInfo().getId(), 13),
                () -> assertEquals(jobInfo.getUser().getId(), 100)
        );
    }

    @Test
    void 채용정보_스크랩_해제() {

        // given
        JobInfoScrapRequest request = new JobInfoScrapRequest(12L, "test@test.com");

        //when
        String response = jobInfoService.jobInfoScrap(request);

        // then
        assertEquals(response, "채용정보 스크랩을 취소하였습니다.");

    }
}