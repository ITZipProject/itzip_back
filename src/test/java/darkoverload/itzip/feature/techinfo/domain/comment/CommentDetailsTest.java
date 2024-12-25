package darkoverload.itzip.feature.techinfo.domain.comment;

import darkoverload.itzip.feature.user.domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CommentDetailsTest {

    @Test
    void 댓글과_작성자_정보로_상세_정보를_생성한다() {
        // given
        Comment comment = Comment.builder()
                .id("675979e6605cda1eaf5d4c19")
                .postId("675979e6605cda1eaf5d4c17")
                .userId(100L)
                .content("This is a test comment.")
                .isPublic(true)
                .createDate(LocalDateTime.now())
                .build();

        User user = User.builder()
                .nickname("test_user")
                .imageUrl("/images/profile.jpg")
                .build();

        // when
        CommentDetails result = CommentDetails.from(comment, user);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCommentId()).isEqualTo(comment.getId());
        assertThat(result.getProfileImagePath()).isEqualTo(user.getImageUrl());
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getContent()).isEqualTo(comment.getContent());
        assertThat(result.getCreateDate()).isEqualTo(comment.getCreateDate());
    }

}
