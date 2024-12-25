package darkoverload.itzip.feature.techinfo.domain.post;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.controller.post.request.PostCreateRequest;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

class PostTest {

    @Test
    void 게시글_생성_시_모든_필드가_올바르게_매핑된다() {
        // given
        String id = "675979e6605cda1eaf5d4c17";
        Long blogId = 1L;
        String categoryId = "66ce18d84cb7d0b29ce602f5";
        String title = "밤하늘 아래, 감정의 여정";
        String content = "세 개의 이미지는 감정의 여정을 표현한다.";
        Integer viewCount = 0;
        Integer likeCount = 0;
        boolean isPublic = true;
        LocalDateTime createDate = LocalDateTime.now();
        String thumbnailImagePath = "/images/thumbnail.jpg";
        List<String> contentImagePaths = List.of(
                "/images/content1.jpg",
                "/images/content2.jpg"
        );

        // when
        Post result = Post.builder()
                .id(id)
                .blogId(blogId)
                .categoryId(categoryId)
                .title(title)
                .content(content)
                .viewCount(viewCount)
                .likeCount(likeCount)
                .isPublic(isPublic)
                .createDate(createDate)
                .thumbnailImagePath(thumbnailImagePath)
                .contentImagePaths(contentImagePaths)
                .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getBlogId()).isEqualTo(blogId);
        assertThat(result.getCategoryId()).isEqualTo(categoryId);
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getViewCount()).isEqualTo(viewCount);
        assertThat(result.getLikeCount()).isEqualTo(likeCount);
        assertThat(result.getIsPublic()).isTrue();
        assertThat(result.getCreateDate()).isEqualTo(createDate);
        assertThat(result.getThumbnailImagePath()).isEqualTo(thumbnailImagePath);
        assertThat(result.getContentImagePaths()).isEqualTo(contentImagePaths);
    }

    @Test
    void 게시글_작성_요청으로_새로운_게시글을_생성한다() {
        // given
        Long blogId = 1L;
        PostCreateRequest request = PostCreateRequest.builder()
                .categoryId("66ce18d84cb7d0b29ce602f5")
                .title("밤하늘 아래, 감정의 여정")
                .content("세 개의 이미지는 감정의 여정을 표현한다.")
                .thumbnailImagePath("/images/thumbnail.jpg")
                .contentImagePaths(List.of(
                        "/images/content1.jpg",
                        "/images/content2.jpg"
                ))
                .build();

        // when
        Post result = Post.from(request, blogId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getBlogId()).isEqualTo(blogId);
        assertThat(result.getCategoryId()).isEqualTo(request.categoryId());
        assertThat(result.getTitle()).isEqualTo(request.title());
        assertThat(result.getContent()).isEqualTo(request.content());
        assertThat(result.getThumbnailImagePath()).isEqualTo(request.thumbnailImagePath());
        assertThat(result.getContentImagePaths()).isEqualTo(request.contentImagePaths());
    }

}
