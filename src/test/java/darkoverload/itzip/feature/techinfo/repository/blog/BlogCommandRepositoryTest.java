package darkoverload.itzip.feature.techinfo.repository.blog;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import darkoverload.itzip.feature.techinfo.service.blog.port.BlogCommandRepository;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.Authority;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@SqlGroup({
        @Sql(value = "/sql/techinfo/blog-repository-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/techinfo/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@Import({
        BlogCommandRepositoryImpl.class,
        BlogReadRepositoryImpl.class
})
@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@TestPropertySource(locations = "classpath:properties/test-env.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BlogCommandRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogCommandRepository blogCommandRepository;

    @Test
    void 새로운_블로그가_주어졌을_때_저장된다() {
        // given
        User owner = User.builder()
                .email("test2@test.com")
                .nickname("아름다운 136번째 돌고래")
                .password("$2a$10$5RifHVaUMq.7IXyJK40kpuaWzfhRBsPgdq1CAhB6LGXdwbxep0.Ba")
                .imageUrl("https://itzip.com")
                .authority(Authority.USER)
                .build();

        Blog blog = Blog.builder()
                .intro("새로운 블로그 소개글")
                .isPublic(true)
                .user(owner)
                .build();

        // when
        userRepository.save(owner.convertToEntity());
        Blog savedBlog = blogCommandRepository.save(blog);

        // then
        assertThat(savedBlog).isNotNull();
        assertThat(savedBlog.getIntro()).isEqualTo(blog.getIntro());
    }

    @Test
    void 사용자_ID와_새로운_소개글이_주어졌을_때_소개글을_업데이트한다() {
        // given
        Long userId = 100L;
        String newIntro = "업데이트된 블로그 소개글";

        // when
        Blog result = blogCommandRepository.update(userId, newIntro);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getIntro()).isEqualTo(newIntro);
    }

    @Test
    void 존재하지_않는_사용자_ID와_새로운_소개글이_주어졌을_때_예외가_발생한다() {
        // given
        Long userId = 101L;
        String newIntro = "업데이트된 블로그 소개글";

        // when & then
        assertThatThrownBy(() -> blogCommandRepository.update(userId, newIntro))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 블로그_ID와_상태가_주어졌을_때_공개_상태가_업데이트된다() {
        // given
        Long blogId = 100L;
        boolean newStatus = false;

        // when
        Blog result = blogCommandRepository.update(blogId, newStatus);

        // then
        assertThat(result).isNotNull();
        assertThat(result.isPublic()).isFalse();
    }

    @Test
    void 존재하지_않는_블로그_ID와_상태가_주어졌을_때_예외가_발생한다() {
        // given
        Long invalidBlogId = 999L;
        boolean newStatus = true;

        // when & then
        assertThatThrownBy(() -> blogCommandRepository.update(invalidBlogId, newStatus))
                .isInstanceOf(RestApiException.class);
    }

}
