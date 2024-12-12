package darkoverload.itzip.feature.techinfo.repository.like;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.dto.like.LikeStatus;
import darkoverload.itzip.feature.techinfo.service.like.port.LikeCacheRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class LikeCacheRepositoryTest {

    @Autowired
    private LikeCacheRepository likeCacheRepository;
    
    private final Long userId = 100L;
    private final String postId = "675979e6605cda1eaf5d4c17";

    @BeforeEach
    void setUp() {
        likeCacheRepository.save(userId, postId, true, 90);
    }

    @AfterEach
    void tearDown() {
        likeCacheRepository.deleteAll();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_저장된다() {
        // given
        Long userId = 101L;
        String postId = "675979e6605cda1eaf5d4c17";

        // when
        likeCacheRepository.save(userId, postId, true, 90);

        // then
        Boolean scrapStatus = likeCacheRepository.getLikeStatus(userId, postId);
        assertThat(scrapStatus).isNotNull();
        assertThat(scrapStatus).isTrue();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_좋아요_상태를_조회한다() {
        // when
        Boolean scrapStatus = likeCacheRepository.getLikeStatus(userId, postId);

        // then
        assertThat(scrapStatus).isNotNull();
        assertThat(scrapStatus).isTrue();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_좋아요_상태가_없으면_null을_반환한다() {
        // given
        Long nonExistentUserId = 102L;
        String nonExistentPostId = "675979e6605cda1eaf5d4c18";

        // when
        Boolean scrapStatus = likeCacheRepository.getLikeStatus(nonExistentUserId, nonExistentPostId);

        // then
        assertThat(scrapStatus).isNull();
    }

    @Test
    void Redis에_저장된_모든_좋아요_상태를_조회한다() {
        // given
        likeCacheRepository.save(103L, "675979e6605cda1eaf5d4c17", true, 90);

        // when
        List<LikeStatus> scrapStatuses = likeCacheRepository.getAllLikeStatuses();

        // then
        assertThat(scrapStatuses).isNotNull();
        assertThat(scrapStatuses).hasSize(2);
    }
    
}
