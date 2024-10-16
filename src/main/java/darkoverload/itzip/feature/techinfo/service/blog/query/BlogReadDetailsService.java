package darkoverload.itzip.feature.techinfo.service.blog.query;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogDetailsResponse;

/**
 * 블로그 세부 정보를 조회하는 서비스를 정의하는 인터페이스.
 * 이 인터페이스는 주어진 닉네임을 기반으로 블로그 세부 정보를 조회하는 메서드를 제공한다.
 */
public interface BlogReadDetailsService {

    /**
     * 닉네임을 사용하여 블로그의 세부 정보를 조회한다.
     * 유저의 인증 정보와 조회할 닉네임을 입력받아 해당 블로그의 세부 정보를 반환한다.
     *
     * @param userDetails 인증된 유저의 정보.
     * @param nickname    조회할 블로그 소유자의 닉네임.
     * @return 조회된 블로그의 세부 정보.
     */
    BlogDetailsResponse getBlogDetailsByNickname(
            CustomUserDetails userDetails,
            String nickname
    );
}