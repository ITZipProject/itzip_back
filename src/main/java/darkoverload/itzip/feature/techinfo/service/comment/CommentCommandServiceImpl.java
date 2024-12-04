package darkoverload.itzip.feature.techinfo.service.comment;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCommentCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCommentUpdateRequest;
import darkoverload.itzip.feature.techinfo.domain.comment.Comment;
import darkoverload.itzip.feature.techinfo.service.comment.port.CommentCommandRepository;
import darkoverload.itzip.feature.techinfo.service.post.PostReadService;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * 댓글 명령(생성, 수정, 삭제) 관련 서비스 구현 클래스.
 */
@Service
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentCommandRepository commentCommandRepository;
    private final UserRepository userRepository;

    private final PostReadService postReadService;

    /**
     * 새로운 댓글을 생성합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @param request     댓글 생성 요청
     * @throws RestApiException 사용자나 포스트를 찾을 수 없을 때 발생
     */
    @Override
    public void create(CustomUserDetails userDetails, PostCommentCreateRequest request) {
        Long userId = getUserId(userDetails);

        if (!postReadService.existsById(new ObjectId(request.postId()))) {
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_POST);
        }

        Comment comment = Comment.from(request, userId);
        commentCommandRepository.save(comment);
    }

    /**
     * 기존 댓글을 수정합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @param request     댓글 수정 요청
     * @throws RestApiException 사용자를 찾을 수 없을 때 발생
     */
    @Override
    public void update(CustomUserDetails userDetails, PostCommentUpdateRequest request) {
        Long userId = getUserId(userDetails);
        commentCommandRepository.update(new ObjectId(request.commentId()), userId, request.content());
    }

    /**
     * 댓글의 공개 상태를 변경합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @param commentId   댓글 ID
     * @param status      새로운 공개 상태
     * @throws RestApiException 사용자를 찾을 수 없을 때 발생
     */
    public void updateVisibility(CustomUserDetails userDetails, String commentId, boolean status) {
        Long userId = getUserId(userDetails);
        commentCommandRepository.update(new ObjectId(commentId), userId, status);
    }

    /**
     * 사용자 이메일로 사용자 ID를 조회합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @return 사용자 ID
     * @throws RestApiException 사용자를 찾을 수 없을 때 발생
     */
    private Long getUserId(CustomUserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER))
                .getId();
    }

}
