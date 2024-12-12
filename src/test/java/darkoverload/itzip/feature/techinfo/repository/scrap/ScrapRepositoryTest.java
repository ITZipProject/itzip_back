package darkoverload.itzip.feature.techinfo.repository.scrap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import darkoverload.itzip.feature.techinfo.domain.scrap.Scrap;
import darkoverload.itzip.feature.techinfo.mock.ScrapMockData;
import darkoverload.itzip.feature.techinfo.service.scrap.port.ScrapRepository;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ScrapRepositoryTest {

    @Autowired
    private ScrapRepository scrapRepository;

    @AfterAll
    static void tearDown(@Autowired ScrapRepository scrapRepository) {
        scrapRepository.deleteAll();
    }

    @Test
    void 새로운_스크랩이_주어졌을_때_저장된다() {
        // given
        String postId = "675979e6605cda1eaf5d4c17";

        // when
        Scrap scrap = scrapRepository.save(ScrapMockData.scrapDataOne);

        // then
        assertThat(scrap).isNotNull();
        assertThat(scrap.getPostId()).isEqualTo(postId);
    }

    @Test
    void 사용자_ID와_스크랩_ID가_주어졌을_때_스크랩이_존재하는지_확인한다() {
        // given
        Long userId = 101L;
        ObjectId postId = new ObjectId("675979e6605cda1eaf5d4c17");
        scrapRepository.save(ScrapMockData.scrapDataSecond);

        // when
        boolean exists = scrapRepository.existsByUserIdAndPostId(userId, postId);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void 사용자_ID와_스크랩_ID가_주어졌을_때_스크랩이_존재하지_않으면_false를_반환한다() {
        // given
        Long userId = 100L;
        ObjectId nonExistentPostId = new ObjectId("675979e6605cda1eaf5d4c18");

        // when
        boolean exists = scrapRepository.existsByUserIdAndPostId(userId, nonExistentPostId);

        // then
        assertThat(exists).isFalse();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_스크랩을_삭제한다() {
        // given
        Long userId = 102L;
        ObjectId postId = new ObjectId("675979e6605cda1eaf5d4c17");
        scrapRepository.save(ScrapMockData.scrapDataThree);

        // when
        scrapRepository.deleteByUserIdAndPostId(userId, postId);

        // then
        boolean exists = scrapRepository.existsByUserIdAndPostId(userId, postId);
        assertThat(exists).isFalse();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_스크랩이_존재하지_않는_경우_예외가_발생한다() {
        // given
        Long userId = 100L;
        ObjectId nonExistentPostId = new ObjectId("675979e6605cda1eaf5d4c18");

        // when & then
        assertThatThrownBy(
                () -> scrapRepository.deleteByUserIdAndPostId(userId, nonExistentPostId)
        ).isInstanceOf(RestApiException.class);
    }

}
