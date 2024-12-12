package darkoverload.itzip.feature.techinfo.repository.blog;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import darkoverload.itzip.feature.techinfo.service.blog.port.BlogReadRepository;
import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.junit.jupiter.api.Test;
import java.util.Optional;

@SqlGroup({
        @Sql(value = "/sql/techinfo/blog-repository-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/techinfo/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@Import(
        BlogReadRepositoryImpl.class
)
@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = TestQueryDslConfig.class)
class BlogReadRepositoryTest {

    @Autowired
    private BlogReadRepository blogReadRepository;

    @Test
    void 블로그ID로_블로그를_조회하면_정상적으로_데이터를_반환한다() {
        // given
        // when
        Optional<Blog> result = blogReadRepository.findByBlogId(100L);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void 존재하지_않는_블로그ID로_조회하면_빈데이터를_반환한다() {
        // given
        // when
        Optional<Blog> result = blogReadRepository.findByBlogId(101L);

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void 사용자ID로_블로그를_조회하면_정상적으로_데이터를_반환한다() {
        // given
        // when
        Optional<Blog> result = blogReadRepository.findByUserId(100L);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void 존재하지_않는_사용자ID로_조회하면_빈데이터를_반환한다() {
        // given
        // when
        Optional<Blog> result = blogReadRepository.findByUserId(101L);

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void 닉네임으로_블로그를_조회하면_정상적으로_데이터를_반환한다() {
        // given
        // when
        Optional<Blog> result = blogReadRepository.findByNickname("아름다운 135번째 돌고래");

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void 존재하지_않는_닉네임을_조회하면_빈데이터를_반환한다() {
        // given
        // when
        Optional<Blog> result = blogReadRepository.findByNickname("아름다운 136번째 돌고래");

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void 존재하는_블로그ID로_조회하면_블로그를_반환한다() {
        // given
        // when
        Blog blog = blogReadRepository.getById(100L);

        // then
        assertThat(blog).isNotNull();
        assertThat(blog.getId()).isEqualTo(100L);
    }

    @Test
    void 존재하지_않는_블로그ID로_조회하면_NOT_FOUND_BLOG_예외가_발생한다() {
        // given
        Long nonExistentId = 999999L;

        // when & then
        assertThatThrownBy(
                () -> blogReadRepository.getById(nonExistentId)
        ).isInstanceOf(RestApiException.class);
    }

    @Test
    void 존재하는_사용자ID로_블로그를_조회하면_블로그를_반환한다() {
        // given
        // when
        Blog blog = blogReadRepository.getById(100L);

        // then
        assertThat(blog).isNotNull();
        assertThat(blog.getId()).isEqualTo(100L);
    }

    @Test
    void 존재하지_않는_사용자ID로_블로그를_조회하면_NOT_FOUND_BLOG_예외가_발생한다() {
        // given
        Long nonExistentId = 999999L;

        // when & then
        assertThatThrownBy(
                () -> blogReadRepository.getById(nonExistentId)
        ).isInstanceOf(RestApiException.class);
    }

    @Test
    void 존재하는_닉네임으로_블로그를_조회하면_블로그를_반환한다() {
        // given
        String nickname = "아름다운 135번째 돌고래";

        // when
        Blog blog = blogReadRepository.getByNickname(nickname);

        // then
        assertThat(blog).isNotNull();
        assertThat(blog.getUser().getNickname()).isEqualTo(nickname);
    }

    @Test
    void 존재하지_않는_닉네임으로_블로그를_조회하면_NOT_FOUND_BLOG_예외가_발생한다() {
        // given
        String nonExistentNickname = "존재하지 않는 닉네임";

        // when & then
        assertThatThrownBy(
                () -> blogReadRepository.getByNickname(nonExistentNickname)
        ).isInstanceOf(RestApiException.class);
    }

}
