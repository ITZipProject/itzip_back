package darkoverload.itzip.feature.techinfo.repository.scrap;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatus;
import darkoverload.itzip.feature.techinfo.service.scrap.port.ScrapCacheRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ScrapCacheRepositoryTest {

    @Autowired
    private ScrapCacheRepository scrapCacheRepository;

    private final Long userId = 100L;
    private final String postId = "675979e6605cda1eaf5d4c17";

    @BeforeEach
    void setUp() {
        scrapCacheRepository.save(userId, postId, true, 90);
    }

    @AfterEach
    void tearDown() {
        scrapCacheRepository.deleteAll();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_저장된다() {
        // given
        Long userId = 101L;
        String postId = "675979e6605cda1eaf5d4c17";

        // when
        scrapCacheRepository.save(userId, postId, true, 90);

        // then
        Boolean scrapStatus = scrapCacheRepository.getScrapStatus(userId, postId);
        assertThat(scrapStatus).isNotNull();
        assertThat(scrapStatus).isTrue();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_스크랩_상태를_조회한다() {
        // when
        Boolean scrapStatus = scrapCacheRepository.getScrapStatus(userId, postId);

        // then
        assertThat(scrapStatus).isNotNull();
        assertThat(scrapStatus).isTrue();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_스크랩_상태가_없으면_null을_반환한다() {
        // given
        Long nonExistentUserId = 102L;
        String nonExistentPostId = "675979e6605cda1eaf5d4c18";

        // when
        Boolean scrapStatus = scrapCacheRepository.getScrapStatus(nonExistentUserId, nonExistentPostId);

        // then
        assertThat(scrapStatus).isNull();
    }

    @Test
    void Redis에_저장된_모든_스크랩_상태를_조회한다() {
        // given
        scrapCacheRepository.save(103L, "675979e6605cda1eaf5d4c17", true, 90);

        // when
        List<ScrapStatus> scrapStatuses = scrapCacheRepository.getAllScrapStatuses();

        // then
        assertThat(scrapStatuses).isNotNull();
        assertThat(scrapStatuses).hasSize(2);
    }

}
