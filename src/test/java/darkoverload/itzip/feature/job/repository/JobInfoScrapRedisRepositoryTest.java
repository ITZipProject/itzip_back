package darkoverload.itzip.feature.job.repository;

import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrapType;
import darkoverload.itzip.feature.job.repository.redis.JobInfoScrapRedisReadRepositoryImpl;
import darkoverload.itzip.feature.job.service.port.JobInfoScrapRedisCommandRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class JobInfoScrapRedisRepositoryTest {

    @Autowired
    private JobInfoScrapRedisReadRepositoryImpl readRepository;
    
    @Autowired
    private JobInfoScrapRedisCommandRepository commandRepository;
    
    private final Long jobInfoId = 11L;
    private final Long unJobInfoId = 13L;
    private final String scrapUserEmail = "text@gmail.com";
    private final String unScrapUserEmail = "text1@gmail.com";

    @BeforeEach
    void setUp() {
        commandRepository.saveScrapInfoToRedis(jobInfoId, scrapUserEmail);
        commandRepository.incrementScrapCountToRedis(jobInfoId);
        commandRepository.saveScrapInfoToRedis(unJobInfoId, unScrapUserEmail);
        commandRepository.unScrapInfoFromRedis(unJobInfoId, unScrapUserEmail);
        commandRepository.decrementScrapCountFromRedis(unJobInfoId);

    }

    @AfterEach
    void before() {
        commandRepository.deleteScrapInfo(jobInfoId, scrapUserEmail);
        commandRepository.deleteScrapInfo(unJobInfoId, unScrapUserEmail);
        commandRepository.deleteScrapCountToRedis(jobInfoId);
        commandRepository.deleteScrapCountToRedis(unJobInfoId);
    }

    @Test
    void 전체_채용_공고_스크랩_레디스_조회_성공_테스트() {
        Set<String> list = readRepository.getJobInfoScrapListFromRedis();

        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void 사람인_채용공고_스크랩_레디스_상태_조회_True_테스트() {
        assertThat(readRepository.getJobInfoStatus(jobInfoId, scrapUserEmail)).isEqualTo(JobInfoScrapType.SCRAP.name());
    }

    @Test
    void 사람인_채용공고_스크랩_레디스_상태_조회_False_테스트() {
        assertThat(readRepository.getJobInfoStatus(unJobInfoId, unScrapUserEmail)).isEqualTo(JobInfoScrapType.UN_SCRAP.name());
    }

    @Test
    void 사람인_채용공고_스크랩_레디스_존재_여부_True_테스트() {
        assertThat(readRepository.hasSameJobInfoScrap(jobInfoId, scrapUserEmail)).isTrue();
    }

    @Test
    void 사람인_채용공고_레디스_스크랩_NULL_UN_SCARP_True_성공_테스트() {
        assertThat(readRepository.isJobInfoScrapStatus(jobInfoId, scrapUserEmail)).isTrue();
    }

    @Test
    void 사람인_채용공고_레디스_스크랩_NULL_UN_SCARP_False_성공_테스트() {
        assertThat(readRepository.isJobInfoScrapStatus(unJobInfoId, unScrapUserEmail)).isFalse();
    }

    @Test
    void 사람인_채용공고_스크랩_레디스_조회수_증가_테스트() {
        assertThat(readRepository.getJobInfoScrapCount(jobInfoId)).isEqualTo("1");
    }

    @Test
    void 사람인_채용공고_스크랩_레디스_조회수_감소_테스트() {
        assertThat(readRepository.getJobInfoScrapCount(unJobInfoId)).isEqualTo("-1");
    }

}
