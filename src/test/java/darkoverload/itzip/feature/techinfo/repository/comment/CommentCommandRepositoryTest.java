package darkoverload.itzip.feature.techinfo.repository.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import darkoverload.itzip.feature.techinfo.mock.CommentMockData;
import darkoverload.itzip.feature.techinfo.service.comment.port.CommentCommandRepository;
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
class CommentCommandRepositoryTest {

    @Autowired
    private CommentCommandRepository commentCommandRepository;

    @BeforeAll
    static void setUp(@Autowired CommentCommandRepository commentCommandRepository) {
        commentCommandRepository.save(CommentMockData.commentDataFour);
    }

    @AfterAll
    static void tearDown(@Autowired CommentCommandRepository commentCommandRepository) {
        commentCommandRepository.deleteAll();
    }

    @Test
    void 새로운_게시글이_주어졌을_때_저장된다() {
        // given
        String content = "test1";

        // when
        Comment result = commentCommandRepository.save(CommentMockData.commentDataOne);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(content);
    }

    @Test
    void 여러_댓글이_주어졌을_때_저장된다() {
        // given
        // when
        List<Comment> result = commentCommandRepository.saveAll(
                List.of(
                        CommentMockData.commentDataSecond,
                        CommentMockData.commentDataThree
                )
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
    }

    @Test
    void 댓글_ID와_사용자_ID_그리고_업데이트_데이터가_주어졌을_때_댓글이_업데이트된다() {
        // given
        ObjectId commentId = new ObjectId("675979e6605cda1eaf5d4c19");
        Long userId = 103L;
        String newContent = "Updated comment content";

        // when
        Comment result = commentCommandRepository.update(commentId, userId, newContent);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(newContent);
    }

    @Test
    void 존재하지_않는_댓글_업데이트시_예외가_발생한다() {
        // given
        ObjectId nonExistentCommentId = new ObjectId("000000000000000000000000");
        Long userId = 103L;
        String newContent = "Updated comment content";

        // when & then
        assertThatThrownBy(
                () -> commentCommandRepository.update(nonExistentCommentId, userId, newContent)
        ).isInstanceOf(RestApiException.class);
    }

    @Test
    void 댓글_ID와_사용자_ID_그리고_공개_상태가_주어졌을_때_상태가_업데이트된다() {
        // given
        ObjectId commentId = new ObjectId("675979e6605cda1eaf5d4c19");
        Long userId = 103L;
        boolean newStatus = false;

        // when
        Comment result = commentCommandRepository.update(commentId, userId, newStatus);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getIsPublic()).isFalse();
    }

    @Test
    void 존재하지_않는_댓글_공개_상태_업데이트시_예외가_발생한다() {
        // given
        ObjectId nonExistentCommentId = new ObjectId("000000000000000000000000");
        Long userId = 103L;
        boolean newStatus = false;

        // when & then
        assertThatThrownBy(
                () -> commentCommandRepository.update(nonExistentCommentId, userId, newStatus)
        ).isInstanceOf(RestApiException.class);
    }

}
