package darkoverload.itzip.feature.techinfo.domain.comment;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.controller.post.request.PostCommentCreateRequest;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class CommentTest {

    @Test
    void 댓글_생성_시_모든_필드가_올바르게_매핑된다() {
        // given
        String id = "675979e6605cda1eaf5d4c19";
        String postId = "675979e6605cda1eaf5d4c17";
        Long userId = 103L;
        String content = "test";
        Boolean isPublic = true;
        LocalDateTime createDate = LocalDateTime.now();

        // when
        Comment result = Comment.builder()
                .id(id)
                .postId(postId)
                .userId(userId)
                .content(content)
                .isPublic(isPublic)
                .createDate(createDate)
                .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getPostId()).isEqualTo(postId);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getIsPublic()).isEqualTo(isPublic);
        assertThat(result.getCreateDate()).isEqualTo(createDate);
    }

    @Test
    void 댓글_작성_요청_시_새로운_댓글을_생성한다() {
        // given
        Long userId = 100L;

        PostCommentCreateRequest request = PostCommentCreateRequest.builder()
                .postId("675979e6605cda1eaf5d4c17")
                .content("This is a comment")
                .build();

        // when
        Comment result = Comment.from(request, userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getPostId()).isEqualTo(request.postId());
        assertThat(result.getContent()).isEqualTo(request.content());
    }

}