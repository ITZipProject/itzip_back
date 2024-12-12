package darkoverload.itzip.feature.techinfo.repository.like;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import darkoverload.itzip.feature.techinfo.domain.like.Like;
import darkoverload.itzip.feature.techinfo.mock.LikeMockData;
import darkoverload.itzip.feature.techinfo.service.like.port.LikeRepository;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    @AfterAll
    static void tearDown(@Autowired LikeRepository likeRepository) {
        likeRepository.deleteAll();
    }

    @Test
    void 새로운_좋아요가_주어졌을_때_저장된다() {
        // given
        String postId = "675979e6605cda1eaf5d4c17";

        // when
        Like like = likeRepository.save(LikeMockData.likeDataOne);

        // then
        assertThat(like).isNotNull();
        assertThat(like.getPostId()).isEqualTo(postId);
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_좋아요가_존재하는지_확인한다() {
        // given
        Long userId = 101L;
        ObjectId postId = new ObjectId("675979e6605cda1eaf5d4c17");
        likeRepository.save(LikeMockData.likeDataSecond);

        // when
        boolean exists = likeRepository.existsByUserIdAndPostId(userId, postId);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_좋아요가_존재하지_않으면_false를_반환한다() {
        // given
        Long userId = 100L;
        ObjectId nonExistentPostId = new ObjectId("675979e6605cda1eaf5d4c18");

        // when
        boolean exists = likeRepository.existsByUserIdAndPostId(userId, nonExistentPostId);

        // then
        assertThat(exists).isFalse();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_좋아요를_삭제한다() {
        // given
        Long userId = 102L;
        ObjectId postId = new ObjectId("675979e6605cda1eaf5d4c17");
        likeRepository.save(LikeMockData.likeDataThree);

        // when
        likeRepository.deleteByUserIdAndPostId(userId, postId);

        // then
        boolean exists = likeRepository.existsByUserIdAndPostId(userId, postId);
        assertThat(exists).isFalse();
    }

    @Test
    void 사용자_ID와_게시글_ID가_주어졌을_때_좋아요가_존재하지_않는_경우_예외가_발생한다() {
        // given
        Long userId = 100L;
        ObjectId nonExistentPostId = new ObjectId("675979e6605cda1eaf5d4c18");

        // when & then
        assertThatThrownBy(
                () -> likeRepository.deleteByUserIdAndPostId(userId, nonExistentPostId)
        ).isInstanceOf(RestApiException.class);
    }

}
