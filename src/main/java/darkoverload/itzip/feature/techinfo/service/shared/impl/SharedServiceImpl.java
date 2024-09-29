package darkoverload.itzip.feature.techinfo.service.shared.impl;

import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.techinfo.repository.blog.BlogRepository;
import darkoverload.itzip.feature.techinfo.service.shared.SharedService;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.service.UserService;
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
public class SharedServiceImpl implements SharedService {

    private final BlogRepository blogRepository;
    private final UserService userService;

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findByIdAndIsPublic(id)
                .map(BlogEntity::convertToDomain)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG)
                );
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.findByEmail(email)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                );
    }

    @Override
    public User getUserByNickname(String nickname) {
        return userService.findByNickname(nickname)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                );
    }

    @Override
    public User getUserById(Long userId) {
        return userService.getById(userId);
    }
}