package darkoverload.itzip.feature.techinfo.domain.post;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.mock.PostMockData;
import darkoverload.itzip.feature.user.domain.User;
import org.junit.jupiter.api.Test;

class PostInfoTest {

    @Test
    void 게시글과_작성자_정보로_기본_정보가_생성된다() {
        // given
        Post post = PostMockData.postDataOne;

        User user = User.builder()
                .email("test@test.com")
                .nickname("test")
                .build();

        // when
        PostInfo result = PostInfo.from(post, user);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getProfileImagePath()).isEqualTo(user.getImageUrl());
        assertThat(result.getAuthor()).isEqualTo(user.getNickname());
        assertThat(result.getPostId()).isEqualTo(post.getId());
        assertThat(result.getCategoryId()).isEqualTo(post.getCategoryId());
        assertThat(result.getCreateDate()).isEqualTo(post.getCreateDate());
        assertThat(result.getTitle()).isEqualTo(post.getTitle());
        assertThat(result.getContent()).isEqualTo(post.getContent());
        assertThat(result.getLikeCount()).isEqualTo(post.getLikeCount());
        assertThat(result.getThumbnailImagePath()).isEqualTo(post.getThumbnailImagePath());
    }

}
