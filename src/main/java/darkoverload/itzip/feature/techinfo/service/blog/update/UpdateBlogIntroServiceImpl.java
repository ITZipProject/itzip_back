package darkoverload.itzip.feature.techinfo.service.blog.update;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.request.UpdateBlogIntroRequest;
import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.repository.blog.BlogRepository;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogService;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateBlogIntroServiceImpl implements UpdateBlogIntroService {

    // 유저 정보를 다루는 리포지토리
    private final UserRepository userRepository;

    // 블로그 정보를 저장하고 관리하는 리포지토리
    private final BlogRepository blogRepository;

    // 블로그 정보를 조회하는 서비스
    private final FindBlogService findBlogService;

    public UpdateBlogIntroServiceImpl(
            UserRepository userRepository,
            BlogRepository blogRepository,
            @Qualifier("findBlogServiceImpl") FindBlogService findBlogService
    ) {
       this.userRepository = userRepository;
       this.blogRepository = blogRepository;
       this.findBlogService = findBlogService;
    }

    @Transactional
    public void updateBlogIntro(CustomUserDetails userDetails, UpdateBlogIntroRequest request) {

        // 유저의 이메일을 기반으로 유저 정보를 조회한 후, 없을 경우 예외 처리
        Long userId = userRepository.findByEmail(userDetails.getEmail())
                .map(UserEntity::convertToDomain) // 엔티티를 도메인 객체로 변환
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER) // 유저를 찾을 수 없을 때 예외 발생
                )
                .getId();

        // 공통 BlogFinderService를 사용하여 유저 ID로 블로그를 조회
        Blog blog = findBlogService.findBlogById(userId);

        // 블로그 소개글을 업데이트
        blog.setIntro(request.getIntro());

        // 업데이트된 블로그 정보를 DB에 저장
        blogRepository.save(blog.convertToEntity());
    }
}