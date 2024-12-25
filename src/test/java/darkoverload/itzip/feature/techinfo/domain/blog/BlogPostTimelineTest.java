package darkoverload.itzip.feature.techinfo.domain.blog;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.feature.techinfo.mock.PostMockData;
import org.junit.jupiter.api.Test;

import java.util.List;

class BlogPostTimelineTest {

    @Test
    void 닉네임과_게시글_목록으로_블로그_게시글_타임라인을_생성한다() {
        // given
        String nickname = "techblogger";
        Post post = PostMockData.postDataOne;

        // when
        BlogPostTimeline result = BlogPostTimeline.from(nickname, List.of(post));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getNickname()).isEqualTo(nickname);
        assertThat(result.getPosts().size()).isEqualTo(1);
        assertThat(result.getPosts().getFirst().getTitle()).isEqualTo(post.getTitle());
        assertThat(result.getPosts().getFirst().getContent()).isEqualTo(post.getContent());
        assertThat(result.getPosts().getFirst().getIsPublic()).isTrue();
    }

    @Test
    void 빈_목록으로_타임라인_생성시_빈_목록_반환한다() {
        // given
        String nickname = "emptyblogger";

        // when
        BlogPostTimeline result = BlogPostTimeline.from(nickname, List.of());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getNickname()).isEqualTo(nickname);
        assertThat(result.getPosts()).isEmpty();
    }

}
