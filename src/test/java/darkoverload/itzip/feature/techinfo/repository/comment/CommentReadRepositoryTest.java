package darkoverload.itzip.feature.techinfo.repository.comment;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import darkoverload.itzip.feature.techinfo.mock.CommentMockData;
import darkoverload.itzip.feature.techinfo.mock.PostMockData;
import darkoverload.itzip.feature.techinfo.service.comment.port.CommentCommandRepository;
import darkoverload.itzip.feature.techinfo.service.comment.port.CommentReadRepository;
import darkoverload.itzip.feature.techinfo.service.post.port.PostCommandRepository;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.feature.techinfo.util.SortUtil;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class CommentReadRepositoryTest {

    @Autowired
    private CommentReadRepository commentReadRepository;

    @BeforeAll
    static void setUp(
            @Autowired PostCommandRepository postCommandRepository,
            @Autowired CommentCommandRepository commentCommandRepository
    ) {
        postCommandRepository.save(PostMockData.postDataFive);
        commentCommandRepository.saveAll(
                List.of(
                        CommentMockData.commentDataOne,
                        CommentMockData.commentDataSecond,
                        CommentMockData.commentDataThree
                )
        );
    }

    @Test
    void 게시글_ID로_댓글을_페이징하여_조회한다() {
        // given
        ObjectId postId = new ObjectId("675979e6605cda1eaf5d4c17");
        int page = 0;
        int size = 10;
        Sort sort = SortUtil.getType(SortType.NEWEST);
        Pageable pageable = PageRequest.of(page, size, sort);

        // when
        Page<Comment> result = commentReadRepository.findCommentsByPostId(postId, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(3);
    }

}
