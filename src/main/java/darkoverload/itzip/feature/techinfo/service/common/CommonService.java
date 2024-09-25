package darkoverload.itzip.feature.techinfo.service.common;

import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.repository.blog.BlogRepository;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공통적으로 사용되는 블로그 및 사용자 정보 조회 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // 읽기 전용 트랜잭션 설정
public class CommonService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    /**
     * 블로그 ID를 이용하여 공개된 블로그 정보를 조회
     *
     * @param blogId 조회할 블로그의 고유 ID
     * @return 조회된 블로그 정보 (도메인 객체)
     * @throws RestApiException 블로그를 찾을 수 없는 경우 예외 발생
     */
    public Blog getBlogById(Long blogId) {
        return blogRepository.findByIdAndIsPublic(blogId)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG))
                .convertToDomain();
    }

    /**
     * 사용자 ID를 이용하여 해당 사용자의 블로그 정보를 조회
     *
     * @param userId 조회할 사용자의 고유 ID
     * @return 조회된 블로그 정보 (도메인 객체)
     * @throws RestApiException 블로그를 찾을 수 없는 경우 예외 발생
     */
    public Blog getBlogByUserId(Long userId) {
        return blogRepository.findByUserIdAndIsPublic(userId)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG))
                .convertToDomain();
    }

    /**
     * 사용자 ID를 이용하여 사용자의 정보를 조회
     *
     * @param userId 조회할 사용자의 고유 ID
     * @return 조회된 사용자 정보 (도메인 객체)
     * @throws RestApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER))
                .convertToDomain();
    }

    /**
     * 닉네임을 이용하여 사용자의 정보를 조회
     *
     * @param nickname 조회할 사용자의 닉네임
     * @return 조회된 사용자 정보 (도메인 객체)
     * @throws RestApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER))
                .convertToDomain();
    }
}