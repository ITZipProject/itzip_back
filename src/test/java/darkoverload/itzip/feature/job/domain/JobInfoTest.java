package darkoverload.itzip.feature.job.domain;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JobInfoTest {

    @Test
    void 채용공고_레디스_키_생성_테스트() {
        assertThat(JobInfo.makeScrapCountRedisKey(13L)).isEqualTo("jobScrapCount:13");
    }

    @Test
    void 채용공고_스크랩_카운트_업데이트_테스트() {
        JobInfo jobInfo = JobInfo.builder()
                .scrapCount(0)
                .build();
        assertThat(jobInfo.updateScrapCount(1)).isEqualTo(1);
    }

}
