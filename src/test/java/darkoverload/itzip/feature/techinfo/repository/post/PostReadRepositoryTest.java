package darkoverload.itzip.feature.techinfo.repository.post;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import darkoverload.itzip.feature.techinfo.mock.PostMockData;
import darkoverload.itzip.feature.techinfo.service.post.port.PostCommandRepository;
import darkoverload.itzip.feature.techinfo.service.post.port.PostReadRepository;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.feature.techinfo.util.SortUtil;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class PostReadRepositoryTest {

    @Autowired
    private PostReadRepository postReadRepository;

    @BeforeAll
    static void setUp(
            @Autowired
            PostCommandRepository postCommandRepository
    ) {
        postCommandRepository.saveAll(
                List.of(
                        PostMockData.postDataOne,
                        PostMockData.postDataSecond,
                        PostMockData.postDataThree,
                        PostMockData.postDataFour,
                        PostMockData.postDataFive
                )
        );
    }

    @AfterAll
    static void tearDown(@Autowired PostCommandRepository postCommandRepository) {
        postCommandRepository.deleteAll();
    }

    @Test
    void ID로_게시글을_조회한다() {
        // given
        ObjectId postId = new ObjectId("675979e6605cda1eaf5d4c17");

        // when
        Optional<Post> result = postReadRepository.findById(postId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(PostMockData.postDataFive.getId());
    }

    @Test
    void 모든_공개_게시글을_페이징하여_조회한다() {
        // given
        int page = 0;
        int size = 12;
        Sort sort = SortUtil.getType(SortType.NEWEST);
        Pageable pageable = PageRequest.of(page, size, sort);

        // when
        Page<Post> result = postReadRepository.findAll(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(5);
    }

    @Test
    void 블로그_ID로_게시글을_페이징하여_조회한다() {
        // given
        Long blogId = 100L;
        int page = 0;
        int size = 12;
        Sort sort = SortUtil.getType(SortType.NEWEST);
        Pageable pageable = PageRequest.of(page, size, sort);

        // when
        Page<Post> result = postReadRepository.findPostsByBlogId(blogId, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(5);
    }

    @Test
    void 카테고리_ID로_게시글을_페이징하여_조회한다() {
        // given
        ObjectId categoryId = new ObjectId("66ce18d84cb7d0b29ce602f5");
        int page = 0;
        int size = 12;
        Sort sort = SortUtil.getType(SortType.NEWEST);
        Pageable pageable = PageRequest.of(page, size, sort);

        // when
        Page<Post> result = postReadRepository.findPostsByCategoryId(categoryId, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(5);
    }

    @Test
    void 날짜_범위로_게시글을_조회한다() {
        // given
        Long blogId = 100L;
        LocalDateTime date = LocalDateTime.now();
        int limit = 4;

        // when
        List<Post> result = postReadRepository.findPostsByDateRange(blogId, date, limit);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(4);
    }

    @Test
    void 블로그_ID로_연간_게시글_통계를_조회한다() {
        // given
        Long blogId = 100L;

        // when
        List<YearlyPostStats> result = postReadRepository.findYearlyPostStatsByBlogId(blogId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFirst().getYear()).isEqualTo(2025);
    }

}