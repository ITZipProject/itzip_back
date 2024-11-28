package darkoverload.itzip.feature.techinfo.service.comment;

import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import darkoverload.itzip.feature.techinfo.domain.comment.CommentDetails;
import darkoverload.itzip.feature.techinfo.service.comment.port.CommentReadRepository;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.feature.techinfo.util.SortUtil;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 댓글 조회 관련 서비스 구현 클래스.
 */
@Service
@RequiredArgsConstructor
public class CommentReadServiceImpl implements CommentReadService {

    private final CommentReadRepository commentReadRepository;
    private final UserRepository userRepository;

    /**
     * 포스트 ID로 댓글을 조회합니다.
     *
     * @param postId   포스트 ID
     * @param pageable 페이징 정보
     * @return Page<CommentDetails>
     */
    @Override
    public Page<Comment> findCommentsByPostId(String postId, Pageable pageable) {
        return commentReadRepository.findCommentsByPostId(postId, pageable);
    }

    /**
     * 포스트 ID로 댓글 상세 정보를 조회합니다.
     *
     * @param postId 포스트 ID
     * @param page   페이지 번호
     * @param size   페이지 크기
     * @return Page<CommentDetails>
     * @throws RestApiException 댓글이나 사용자를 찾을 수 없을 때 발생
     */
    @Override
    public Page<CommentDetails> getCommentsByPostId(String postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, SortUtil.getSort(SortType.NEWEST));

        Page<Comment> comments = findCommentsByPostId(postId, pageable);

        if (comments.isEmpty()) {
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_COMMENT_IN_POST);
        }

        return comments.map(post -> {
            User user = userRepository.findById(post.getUserId())
                    .orElseThrow(
                            () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                    )
                    .convertToDomain();

            return CommentDetails.from(post, user);
        });
    }

}
