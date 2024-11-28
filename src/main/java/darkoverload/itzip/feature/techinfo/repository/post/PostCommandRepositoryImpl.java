package darkoverload.itzip.feature.techinfo.repository.post;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.service.post.port.PostCommandRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MongoDB를 사용하여 포스트 명령(생성, 수정)을 처리하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class PostCommandRepositoryImpl implements PostCommandRepository {

    private final MongoPostCommandRepository repository;

    /**
     * 새로운 포스트를 저장합니다.
     *
     * @param post 저장할 Post
     */
    @Override
    public void save(Post post) {
        repository.save(PostDocument.from(post));
    }

    /**
     * 포스트의 상세 정보를 업데이트합니다.
     *
     * @param postId             포스트 ID
     * @param categoryId         카테고리 ID
     * @param title              제목
     * @param content            내용
     * @param thumbnailImagePath 썸네일 이미지 URL
     * @param contentImagePaths  본문 이미지 URL 목록
     * @throws RestApiException 포스트 업데이트 실패 시 발생
     */
    @Override
    public void update(ObjectId postId, ObjectId categoryId, String title, String content, String thumbnailImagePath,
                       List<String> contentImagePaths) {
        if (repository.update(postId, categoryId, title, content, thumbnailImagePath, contentImagePaths) < 0) {
            throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_POST);
        }
    }

    /**
     * 포스트의 공개 상태를 업데이트합니다.
     *
     * @param postId 포스트 ID
     * @param status 새로운 공개 상태
     * @throws RestApiException 포스트 상태 업데이트 실패 시 발생
     */
    @Override
    public void update(ObjectId postId, boolean status) {
        if (repository.update(postId, status) < 0) {
            throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_POST);
        }
    }

    /**
     * 포스트의 특정 필드 값을 업데이트합니다.
     *
     * @param postId    포스트 ID
     * @param fieldName 업데이트할 필드 이름
     * @param value     새로운 값
     * @throws RestApiException 포스트 필드 업데이트 실패 시 발생
     */
    @Override
    public void updateFieldWithValue(ObjectId postId, String fieldName, int value) {
        if (repository.updateFieldWithValue(postId, fieldName, value) < 0) {
            throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_POST);
        }
    }

}
