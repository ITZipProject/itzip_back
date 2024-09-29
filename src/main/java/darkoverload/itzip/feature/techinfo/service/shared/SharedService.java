package darkoverload.itzip.feature.techinfo.service.shared;

import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.user.domain.User;

public interface SharedService {

    /**
     * 블로그 ID를 이용하여 공개된 블로그 정보를 조회
     *
     * @param id 조회할 블로그의 고유 ID
     * @return 조회된 블로그 정보 (도메인 객체)
     */
    Blog getBlogById(Long id);

    /**
     * 이메일을 이용하여 사용자 정보를 조회
     *
     * @param email 조회할 사용자의 이메일
     * @return 조회된 사용자 정보 (도메인 객체)
     */
    User getUserByEmail(String email);

    /**
     * 닉네임을 이용하여 사용자 정보를 조회
     *
     * @param nickname 조회할 사용자의 닉네임
     * @return 조회된 사용자 정보 (도메인 객체)
     */
    User getUserByNickname(String nickname);

    /**
     * 사용자 ID를 이용하여 사용자 정보를 조회
     *
     * @param userId 조회할 사용자의 고유 ID
     * @return 조회된 사용자 정보 (도메인 객체)
     */
    User getUserById(Long userId);
}