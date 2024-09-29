package darkoverload.itzip.feature.techinfo.service.blog;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.request.BlogUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.response.BlogBasicInfoResponse;
import darkoverload.itzip.feature.techinfo.controller.response.BlogDetailInfoResponse;
import darkoverload.itzip.feature.user.domain.User;

import java.util.Optional;

/**
 * 블로그 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
 * 블로그 생성, 업데이트, 숨김 처리, 블로그 정보 조회와 같은 주요 기능들을 제공함.
 */
public interface BlogService {

    /**
     * 주어진 유저 정보를 기반으로 블로그를 생성한다.
     *
     * @param user 블로그를 생성할 유저 정보
     */
    void createBlog(User user);

    /**
     * 주어진 사용자 정보와 업데이트 요청 데이터를 사용해 블로그 정보를 업데이트한다.
     *
     * @param userDetails  업데이트할 유저의 인증 정보
     * @param blogUpdateDto 블로그 업데이트 요청 객체
     */
    void updateBlog(CustomUserDetails userDetails, BlogUpdateRequest blogUpdateDto);

    /**
     * 현재 사용자의 블로그를 숨김 처리(비공개 상태로 전환)한다.
     *
     * @param customUserDetails 숨김 처리를 요청한 사용자의 인증 정보
     */
    void setBlogHiddenStatus(CustomUserDetails customUserDetails);

    /**
     * 블로그의 기본 정보를 조회한다.
     *
     * @param blogId 조회할 블로그의 ID
     * @return 블로그 기본 정보를 담은 BlogBasicInfoResponse 객체
     */
    BlogBasicInfoResponse getBasicBlogInfo(Long blogId);

    /**
     * 현재 사용자 정보와 주어진 닉네임을 기반으로 블로그의 세부 정보를 조회한다.
     * 닉네임이 제공되지 않은 경우 기본 정보를 반환할 수 있다.
     *
     * @param customUserDetails 세부 정보를 요청한 사용자의 인증 정보
     * @param nickname 조회할 블로그의 닉네임 (Optional)
     * @return 블로그 세부 정보를 담은 BlogDetailInfoResponse 객체
     */
    BlogDetailInfoResponse getDetailBlogInfo(CustomUserDetails customUserDetails, Optional<String> nickname);
}