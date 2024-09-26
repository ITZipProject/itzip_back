package darkoverload.itzip.feature.techinfo.repository.post.custom;

import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.repository.post.PostRepository;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.feature.techinfo.util.SortUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class CustomPostRepositoryImplTest {

    @Autowired
    private PostRepository postRepository;

    private Pageable pageable;

    @BeforeEach
    public void 준비() {
        pageable = PageRequest.of(0, 10, SortUtil.getSort(SortType.NEWEST));
    }

    @Test
    public void 카테고리ID로_게시물_찾기_테스트() {
        // Given
        String categoryId = "66cb63fb00000000003a8225"; // 실제 존재하는 categoryId를 사용하세요.

        // When
        Page<PostDocument> result = postRepository.findPostsByCategoryId(new ObjectId(categoryId), pageable);

        // Then
        assertNotNull(result, "Result is null.");
        assertFalse(result.isEmpty(), "게시물이 존재하지 않습니다.");

        result.forEach(postDocument -> {
            log.info("Post createDate: {}", postDocument.getCreateDate());

            assertAll("PostDocument 필드 검증",
                    () -> assertNotNull(postDocument.getId(), "PostId is null."),
                    () -> assertNotNull(postDocument.getBlogId(), "Blog is null."),
                    () -> assertNotNull(postDocument.getCategoryId(), "CategoryId is null."),
                    () -> assertNotNull(postDocument.getTitle(), "Title is null."),
                    () -> assertNotNull(postDocument.getContent(), "Content is null."),
                    () -> assertNotNull(postDocument.getLikeCount(), "LikeCount is null."),
                    () -> assertNotNull(postDocument.getCreateDate(), "CreateDate is null."),
                    () -> assertNotNull(postDocument.getThumbnailImagePath(), "ThumbnailImagePath is null.")
            );
        });
    }

    @Test
    public void 블로그ID로_게시물_찾기_테스트() {
        // Given
        Long blogId = 1L;

        // When
        Page<PostDocument> result = postRepository.findPostsByBlogId(blogId, pageable);

        // Then
        assertNotNull(result, "Result is null.");
        assertFalse(result.isEmpty(), "게시물이 존재하지 않습니다.");

        result.forEach(postDocument -> {
            assertAll("PostDocument 필드 검증",
                    () -> assertNotNull(postDocument.getId(), "PostId is null."),
                    () -> assertNotNull(postDocument.getCategoryId(), "CategoryId is null."),
                    () -> assertNotNull(postDocument.getTitle(), "Title is null."),
                    () -> assertNotNull(postDocument.getContent(), "Content is null."),
                    () -> assertNotNull(postDocument.getLikeCount(), "LikeCount is null."),
                    () -> assertNotNull(postDocument.getCreateDate(), "CreateDate is null."),
                    () -> assertNotNull(postDocument.getThumbnailImagePath(), "ThumbnailImagePath is null.")
            );
        });
    }
}