package darkoverload.itzip.feature.resume.repository.resume.scrap;

import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrapType;
import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrapType;
import darkoverload.itzip.feature.resume.service.resume.port.resume.redis.ResumeScrapRedisCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.resume.redis.ResumeScrapRedisReadRepository;
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
public class ResumeScrapRedisRepositoryTest {

    @Autowired
    private ResumeScrapRedisReadRepository readRepository;
    @Autowired
    private ResumeScrapRedisCommandRepository commandRepository;

    private final Long resumeId = 1L;
    private final String scrapUserEmail = "test1@itzip.com";
    private final Long unResumeId = 2L;
    private final String unScrapUserEmail = "test2@itzip.com";

    @BeforeEach
    void setUp() {
        commandRepository.saveResumeScrapToRedis(resumeId, scrapUserEmail);
        commandRepository.incrementScrapCountToRedis(resumeId);
        commandRepository.saveResumeScrapToRedis(unResumeId, unScrapUserEmail);
        commandRepository.unResumeScrapFromRedis(unResumeId, unScrapUserEmail);
        commandRepository.decrementScrapCountFromRedis(unResumeId);

    }

    @AfterEach
    void after() {
        commandRepository.deleteResumeInfo(resumeId, scrapUserEmail);
        commandRepository.deleteResumeInfo(unResumeId, unScrapUserEmail);
        commandRepository.deleteResumeCountToRedis(resumeId);
        commandRepository.deleteResumeCountToRedis(unResumeId);
    }

    @Test
    void 전체_이력서_스크랩_레디스_조회_성공_테스트() {
        Set<String> list = readRepository.getResumeScrapListFromRedis();

        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void 이력서_스크랩_레디스_상태_조회_True_테스트() {
        assertThat(readRepository.getResumeStatus(resumeId, scrapUserEmail)).isEqualTo(ResumeScrapType.SCRAP.name());
    }

    @Test
    void 이력서_스크랩_레디스_상태_조회_False_테스트() {
        assertThat(readRepository.getResumeStatus(unResumeId, unScrapUserEmail)).isEqualTo(JobInfoScrapType.UN_SCRAP.name());
    }

    @Test
    void 이력서_스크랩_레디스_존재_여부_True_테스트() {
        assertThat(readRepository.hasSameResumeScrap(resumeId, scrapUserEmail)).isTrue();
    }

    @Test
    void 이력서_레디스_스크랩_NULL_UN_SCARP_True_성공_테스트() {
        assertThat(readRepository.isResumeScrapStatus(resumeId, scrapUserEmail)).isTrue();
    }

    @Test
    void 이력서_레디스_스크랩_NULL_UN_SCARP_False_성공_테스트() {
        assertThat(readRepository.isResumeScrapStatus(unResumeId, unScrapUserEmail)).isFalse();
    }

    @Test
    void 이력서_스크랩_레디스_조회수_증가_테스트() {
        assertThat(readRepository.getResumeScrapCount(resumeId)).isEqualTo("1");
    }

    @Test
    void 이력서_스크랩_레디스_조회수_감소_테스트() {
        assertThat(readRepository.getResumeScrapCount(unResumeId)).isEqualTo("-1");
    }

}
