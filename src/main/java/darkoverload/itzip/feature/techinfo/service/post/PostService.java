package darkoverload.itzip.feature.techinfo.service.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostDetailInfoResponse;

/**
 * 포스트 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
 * 포스트 생성, 수정, 숨김 처리 및 다양한 조회 기능을 제공함.
 */
public interface PostService {

    /**
     * 새로운 포스트를 추가하는 메서드.
     *
     * @param request 포스트 생성 요청을 담은 객체
     */
    void addNewPost(CustomUserDetails customUserDetails, PostCreateRequest request);

    /**
     * 기존 포스트를 수정하는 메서드.
     *
     * @param request 포스트 수정 요청을 담은 객체
     */
    void modifyPost(PostUpdateRequest request);

    /**
     * 포스트를 숨기는 메서드. 게시물은 삭제되지 않고 공개 여부만 변경됩니다.
     *
     * @param postId 숨길 포스트의 고유 ID
     */
    void hidePost(String postId);

    /**
     * 포스트의 세부 정보를 ID를 통해 조회하는 메서드.
     *
     * @param
     * @param postId   조회할 포스트의 고유 ID
     * @return 포스트 세부 정보가 담긴 응답 객체
     */
    PostDetailInfoResponse getPostDetailById(CustomUserDetails userDetails, String postId);
}