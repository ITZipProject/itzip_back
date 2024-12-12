package darkoverload.itzip.feature.techinfo.repository.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.feature.techinfo.mock.PostMockData;
import darkoverload.itzip.feature.techinfo.service.post.port.PostCommandRepository;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class PostCommandRepositoryTest {

    @Autowired
    private PostCommandRepository postCommandRepository;

    @BeforeAll
    static void setUp(@Autowired PostCommandRepository postCommandRepository) {
        postCommandRepository.save(PostMockData.postDataFive);
    }

    @AfterAll
    static void tearDown(@Autowired PostCommandRepository postCommandRepository) {
        postCommandRepository.deleteAll();
    }

    @Test
    void 새로운_게시글이_주어졌을_때_저장된다() {
        // given
        long blogId = 100L;

        // when
        Post result = postCommandRepository.save(PostMockData.postDataOne);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getBlogId()).isEqualTo(blogId);
    }

    @Test
    void 여러_게시글이_주어졌을_때_저장된다() {
        // given
        // when
        List<Post> result = postCommandRepository.saveAll(
                List.of(
                        PostMockData.postDataSecond,
                        PostMockData.postDataThree,
                        PostMockData.postDataFour
                )
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
    }

    @Test
    void 게시글_ID와_업데이트_데이터가_주어졌을_때_게시글이_업데이트된다() {
        // given
        ObjectId postId = new ObjectId("675979e6605cda1eaf5d4c17");
        ObjectId categoryId = new ObjectId("66ce18d84cb7d0b29ce602f5");
        String title = "Updated Title";
        String content = "Updated Content";
        String thumbnailImageUrl = "https://dy1vg9emkijkn.cloudfront.net/techinfo/19cc111f-c8f4-4d64-bd7a-129415e3ffa2.jpg";
        List<String> contentImageUrls = List.of(
                "https://dy1vg9emkijkn.cloudfront.net/techinfo/7635bb80-416a-4042-a901-552df46351a8.png",
                "https://dy1vg9emkijkn.cloudfront.net/techinfo/50d081ca-b2f5-4162-926f-0f061aec2554.png"
        );

        // when
        Post result = postCommandRepository.update(postId, categoryId, title, content, thumbnailImageUrl, contentImageUrls);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(postId.toHexString());
    }

    @Test
    void 존재하지_않는_게시글_상세_업데이트시_예외가_발생한다() {
        // given
        ObjectId nonExistentPostId = new ObjectId("000000000000000000000000");
        ObjectId categoryId = new ObjectId("66ce18d84cb7d0b29ce602f5");
        String title = "Non-existent Title";
        String content = "Non-existent Content";
        String thumbnailImageUrl = "https://example.com/thumbnail.jpg";
        List<String> contentImageUrls = List.of("https://example.com/image1.jpg");

        // when & then
        assertThatThrownBy(
                () -> postCommandRepository.update(nonExistentPostId, categoryId, title, content, thumbnailImageUrl, contentImageUrls)
        ).isInstanceOf(RestApiException.class);
    }

    @Test
    void 게시글_ID와_공개_상태가_주어졌을_때_상태가_업데이트된다() {
        // given
        ObjectId postId = new ObjectId("675979e6605cda1eaf5d4c17");
        boolean status = false;

        // when
        Post result = postCommandRepository.update(postId, status);

        assertThat(result).isNotNull();
        assertThat(result.getIsPublic()).isFalse();
    }

    @Test
    void 존재하지_않는_게시글_공개_상태_업데이트시_예외가_발생한다() {
        // given
        ObjectId nonExistentPostId = new ObjectId("000000000000000000000001");
        boolean status = false;

        // when & then
        assertThatThrownBy(() ->
                postCommandRepository.update(nonExistentPostId, status)
        ).isInstanceOf(RestApiException.class);
    }

    @Test
    void 게시글_ID와_특정_필드값이_주어졌을_때_필드가_업데이트된다() {
        // given
        ObjectId postId = new ObjectId("675979e6605cda1eaf5d4c17");
        String fieldName = "view_count";
        int value = 1;

        // when
        Post result = postCommandRepository.updateFieldWithValue(postId, fieldName, value);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getViewCount()).isEqualTo(value);
    }

    @Test
    void 존재하지_않는_게시글_필드값_업데이트시_예외가_발생한다() {
        // Given
        ObjectId nonExistentPostId = new ObjectId("000000000000000000000002");
        String fieldName = "view_count";
        int value = 1;

        // When & Then
        assertThatThrownBy(() ->
                postCommandRepository.updateFieldWithValue(nonExistentPostId, fieldName, value)
        ).isInstanceOf(RestApiException.class);
    }

}
