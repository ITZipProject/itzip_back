package darkoverload.itzip.feature.techinfo.service.blog.update;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.request.UpdateBlogIntroRequest;

/**
 * 블로그 소개글 업데이트 서비스를 정의하는 인터페이스.
 * 이 인터페이스는 인증된 유저의 블로그 소개글을 업데이트하는 메서드를 제공한다.
 */
public interface UpdateBlogIntroService {

    /**
     * 인증된 유저의 블로그 소개글을 업데이트한다.
     *
     * @param userDetails 업데이트를 요청한 사용자의 인증 정보.
     * @param request     블로그의 새로운 소개글을 포함하는 요청 객체.
     */
    void updateBlogIntro(CustomUserDetails userDetails, UpdateBlogIntroRequest request);
}