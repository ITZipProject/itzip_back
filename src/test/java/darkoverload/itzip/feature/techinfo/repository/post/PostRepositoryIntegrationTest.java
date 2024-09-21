package darkoverload.itzip.feature.techinfo.repository.post;

import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class PostRepositoryIntegrationTest {
    @Autowired
    private PostRepository postRepository;

    private PostDocument postDocument;

    @BeforeEach
    public void setUp() {
        // Given: 테스트에 사용할 데이터 생성
        postDocument = PostDocument.builder()
                .blogId(1L)
                .categoryId(new ObjectId("66ce18d84cb7d0b29ce602f5"))
                .title("블로그 포스트 샘플 데이터 입니다.")
                .content("이 포스트는 Spring Boot와 MongoDB를 통합하는 방법에 대해 설명합니다.")
                .viewCount(0)
                .likeCount(0)
                .isPublic(true)
                .thumbnailImagePath("https://dy1vg9emkijkn.cloudfront.net/temporary/0ec73e4b-230e-4e0f-b709-4c9b157bf521.jpg")
                .contentImagePaths(List.of(
                        "https://dy1vg9emkijkn.cloudfront.net/temporary/7a3f70f3-deed-4629-9a6e-a442d2eddf9f.png"))
                .build();

        postRepository.save(postDocument); // 데이터 저장
    }

    @AfterEach
    public void tearDown() {
        // Test 완료 후 데이터 삭제
        postRepository.delete(postDocument);
    }

    @Test
    public void 게시물_ID_조회() {
        // When
        PostDocument foundPost = postRepository.findById(postDocument.getId()).orElse(null);

        // Then
        assertThat(foundPost).isNotNull();
        assertThat(foundPost.getTitle()).isEqualTo(postDocument.getTitle());
    }
}