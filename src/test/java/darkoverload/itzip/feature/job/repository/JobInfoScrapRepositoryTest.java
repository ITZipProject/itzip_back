package darkoverload.itzip.feature.job.repository;

import darkoverload.itzip.feature.job.entity.JobInfoScrapEntity;
import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SqlGroup({
        @Sql(value = "/sql/jobinfo/scrap/delete-infoscrap-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(value = "/sql/jobinfo/scrap/infoscrap-repository-date.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
class JobInfoScrapRepositoryTest {

    @Autowired
    private JobInfoScrapRepository jobInfoScrapRepository;

    @Test
    void 채용공고_스크랩_확인(){

        // given
        // when
        JobInfoScrapEntity jobInfo = jobInfoScrapRepository.findByJobInfoId(2L, "test@test.com").get();


        // then
        assertEquals(jobInfo.getId(), 100L);
    }



}