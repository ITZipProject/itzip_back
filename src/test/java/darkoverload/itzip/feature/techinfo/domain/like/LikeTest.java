package darkoverload.itzip.feature.techinfo.domain.like;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.dto.like.LikeStatus;
import org.junit.jupiter.api.Test;

class LikeTest {

    @Test
    void 좋아요_생성_시_모든_필드가_올바르게_매핑된다() {
        // given
        String id = "675979e6605cda1eaf5d4c20";
        String postId = "675979e6605cda1eaf5d4c17";
        Long userId = 100L;

        // when
        Like result = Like.builder()
                .id(id)
                .postId(postId)
                .userId(userId)
                .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getPostId()).isEqualTo(postId);
        assertThat(result.getUserId()).isEqualTo(userId);
    }

    @Test
    void 좋아요_상태로부터_좋아요를_생성한다() {
        // given
        LikeStatus likeStatus = LikeStatus.builder()
                .postId("675979e6605cda1eaf5d4c17")
                .userId(100L)
                .isLiked(true)
                .build();

        // when
        Like result = Like.from(likeStatus);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getPostId()).isEqualTo(likeStatus.getPostId());
        assertThat(result.getUserId()).isEqualTo(likeStatus.getUserId());
    }

}
