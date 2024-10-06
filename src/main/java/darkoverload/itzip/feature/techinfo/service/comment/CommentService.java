package darkoverload.itzip.feature.techinfo.service.comment;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.request.CommentCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.request.CommentUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.response.CommentResponse;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

/**
 * 댓글 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
 * 댓글 생성, 수정, 숨김 처리 및 댓글 목록 조회 기능을 제공함.
 */
public interface CommentService {

    /**
     * 새로운 댓글을 추가하는 메서드.
     *
     * @param request 댓글 생성 요청을 담은 객체
     */
    void addNewComment(CommentCreateRequest request);

    /**
     * 기존 댓글을 수정하는 메서드.
     *
     * @param userDetails 요청을 보낸 사용자의 인증 정보
     * @param request 수정할 댓글 내용을 담은 요청 객체
     */
    void modifyComment(CustomUserDetails userDetails, CommentUpdateRequest request);

    /**
     * 특정 댓글을 숨김 처리하는 메서드. 댓글은 삭제되지 않고 비공개 상태로 전환됨.
     *
     * @param commentId 숨길 댓글의 고유 ID
     */
    void hideComment(CustomUserDetails userDetails, String commentId);

    /**
     * 특정 게시글에 속한 댓글 목록을 조회하는 메서드. 페이지네이션을 지원함.
     *
     * @param postId 조회할 게시글의 ID
     * @param page 페이지 번호
     * @param size 페이지당 댓글 수
     * @return 페이지네이션된 댓글 응답을 담은 PagedModel
     */
    PagedModel<EntityModel<CommentResponse>> getFilteredComments(String postId, int page, int size);
}