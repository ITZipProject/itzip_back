package darkoverload.itzip.feature.techinfo.service.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostUpdateRequest;
import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.feature.techinfo.service.blog.port.BlogReadRepository;
import darkoverload.itzip.feature.techinfo.service.post.port.PostCommandRepository;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 포스트 명령(생성, 수정) 관련 서비스 구현 클래스.
 */
@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final BlogReadRepository blogReadRepository;
    private final PostCommandRepository postCommandRepository;
    private final UserRepository userRepository;

    /**
     * 새로운 포스트를 생성합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @param request     포스트 생성 요청
     * @throws RestApiException 사용자를 찾을 수 없을 때 발생
     */
    @Override
    @Transactional(readOnly = true)
    public void create(CustomUserDetails userDetails, PostCreateRequest request) {
        Long userId = userRepository.findByEmail(userDetails.getEmail())
                .map(UserEntity::getId)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                );

        Long blogId = blogReadRepository.getByUserId(userId)
                .getId();

        Post post = Post.from(request, blogId);
        postCommandRepository.save(post);
    }

    /**
     * 기존 포스트를 수정합니다.
     *
     * @param request 포스트 수정 요청
     */
    @Override
    public void update(PostUpdateRequest request) {
        postCommandRepository.update(
                new ObjectId(request.postId()),
                new ObjectId(request.categoryId()),
                request.title(),
                request.content(),
                request.thumbnailImagePath(),
                request.contentImagePaths()
        );
    }

    /**
     * 포스트의 공개 상태를 변경합니다.
     *
     * @param postId 포스트 ID
     * @param status 새로운 공개 상태
     */
    @Override
    public void update(String postId, boolean status) {
        postCommandRepository.update(new ObjectId(postId), status);
    }

    /**
     * 포스트의 특정 필드 값을 업데이트합니다.
     *
     * @param postId    포스트 ID
     * @param fieldName 업데이트할 필드 이름
     * @param value     새로운 값
     */
    @Override
    public void updateFieldWithValue(String postId, String fieldName, int value) {
        postCommandRepository.updateFieldWithValue(new ObjectId(postId), fieldName, value);
    }

}
