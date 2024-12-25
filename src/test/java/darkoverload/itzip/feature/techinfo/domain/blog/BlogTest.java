package darkoverload.itzip.feature.techinfo.domain.blog;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.user.domain.User;
import org.junit.jupiter.api.Test;

public class BlogTest {

    @Test
    void 블로그_생성_시_모든_필드가_올바르게_매핑된다() {
        // given
        Long blogId = 1L;
        User user = User.builder()
                .email("test@test.com")
                .nickname("test")
                .build();
        String intro = "This is a sample blog";
        boolean isPublic = true;

        // when
        Blog result = Blog.builder()
                .id(blogId)
                .user(user)
                .intro(intro)
                .isPublic(isPublic)
                .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(blogId);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getIntro()).isEqualTo(intro);
        assertThat(result.isPublic()).isEqualTo(isPublic);
    }

    @Test
    void 사용자로부터_블로그_생성_시_기본적으로_공개상태이다() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .nickname("test")
                .build();

        // when
        Blog blog = Blog.from(user);

        // then
        assertThat(blog).isNotNull();
        assertThat(blog.getUser()).isEqualTo(user);
        assertThat(blog.isPublic()).isTrue();
    }

    @Test
    void 블로그가_비공개로_설정되면_isPublic이_false를_반환한다() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .nickname("test")
                .build();

        // when
        Blog result = Blog.builder()
                .id(2L)
                .user(user)
                .intro("This is a sample blog")
                .isPublic(false)
                .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.isPublic()).isFalse();
    }

}
