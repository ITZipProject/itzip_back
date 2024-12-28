package darkoverload.itzip.feature.job.repository;

import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@SqlGroup({
        @Sql(value = "/sql/jobinfo/delete-jobinfo-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(value = "/sql/jobinfo/jobinfo-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
@ActiveProfiles("test")
class JobInfoJpaRepositoryTest {

    @Autowired
    private JobInfoJpaRepository repository;

    @Test
    void 채용정보_페이징_조회() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<JobInfoSearchResponse> jobInfoSearchResponses = repository.searchJobInfo("LG", "모바일", 0, null, null,pageable);
        List<String> jobNameList = Arrays.asList("CDMA","모바일","무선통신","텔레콤","통신","네트워크","정보통신","솔루션");


        JobInfoSearchResponse response = jobInfoSearchResponses.getContent().getFirst();


        assertAll(

                () -> assertEquals(response.getJobName(), jobNameList),
                () -> assertEquals(response.getExperienceMax(), 5L),
                () -> assertEquals(response.getExperienceMin(), 0L),
                () -> assertEquals(response.getTitle(), "[Ericsson-LG] Operative Product Owner 채용")
        );

    }


}