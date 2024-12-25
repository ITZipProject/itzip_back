package darkoverload.itzip.feature.techinfo.domain.blog;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.dto.post.MonthlyPostStats;
import darkoverload.itzip.feature.techinfo.dto.post.WeeklyPostStats;
import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import darkoverload.itzip.feature.user.domain.User;
import org.junit.jupiter.api.Test;
import java.util.List;

class BlogDetailsTest {

    @Test
    void 블로그와_통계정보로_상세정보를_생성한다() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .nickname("test")
                .imageUrl("https://image.url")
                .build();

        Blog blog = Blog.builder()
                .id(1L)
                .user(user)
                .intro("This is a blog intro.")
                .isPublic(true)
                .build();

        YearlyPostStats yearlyPostStats = new YearlyPostStats(2024, EMPTY_LIST);

        // when
        BlogDetails result = BlogDetails.from(blog, List.of(yearlyPostStats));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getBlogId()).isEqualTo(blog.getId());
        assertThat(result.getProfileImageUrl()).isEqualTo(blog.getUser().getImageUrl());
        assertThat(result.getNickname()).isEqualTo(blog.getUser().getNickname());
        assertThat(result.getEmail()).isEqualTo(blog.getUser().getEmail());
        assertThat(result.getIntro()).isEqualTo(blog.getIntro());
        assertThat(result.getYearlyPostCounts().size()).isEqualTo(1);
    }

    @Test
    void 연도별_게시글_통계가_생성된다() {
        // given
        WeeklyPostStats weeklyPostStats = new WeeklyPostStats(1, 1);
        MonthlyPostStats monthlyPostStats = new MonthlyPostStats(1, List.of(weeklyPostStats));

        // when
        YearlyPostStats result = new YearlyPostStats(2024, List.of(monthlyPostStats));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getYear()).isEqualTo(2024);
        assertThat(result.getMonths().size()).isEqualTo(1);
        assertThat(result.getMonths().getFirst().getMonth()).isEqualTo(1);
    }

    @Test
    void 월별_게시글_통계가_생성된다() {
        // given
        WeeklyPostStats weeklyPostStats = new WeeklyPostStats(1, 5);

        // when
        MonthlyPostStats result = new MonthlyPostStats(2, List.of(weeklyPostStats));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getMonth()).isEqualTo(2);
        assertThat(result.getWeeks().size()).isEqualTo(1);
        assertThat(result.getWeeks().getFirst().getWeek()).isEqualTo(1);
        assertThat(result.getWeeks().getFirst().getPostCount()).isEqualTo(5);
    }

    @Test
    void 주차와_게시글_수량으로_주별_게시글_통계를_생성한다() {
        // given & when
        WeeklyPostStats result = new WeeklyPostStats(3, 10);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getWeek()).isEqualTo(3);
        assertThat(result.getPostCount()).isEqualTo(10);
    }

}
