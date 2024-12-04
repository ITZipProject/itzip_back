package darkoverload.itzip.feature.techinfo.repository.like;

import darkoverload.itzip.feature.techinfo.domain.like.Like;
import darkoverload.itzip.feature.techinfo.model.document.LikeDocument;
import darkoverload.itzip.feature.techinfo.repository.like.mongo.MongoLikeRepository;
import darkoverload.itzip.feature.techinfo.service.like.port.LikeRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

/**
 * MongoDB를 사용하여 좋아요 정보를 관리하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    private final MongoLikeRepository repository;

    /**
     * 새로운 좋아요 정보를 저장합니다.
     *
     * @param like 좋아요
     */
    @Override
    public void save(Like like) {
        repository.save(LikeDocument.from(like));
    }

    /**
     * 특정 사용자가 특정 포스트에 좋아요를 눌렀는지 확인합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 좋아요가 존재하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean existsByUserIdAndPostId(Long userId, ObjectId postId) {
        return repository.existsByUserIdAndPostId(userId, postId);
    }

    /**
     * 특정 사용자의 특정 포스트에 대한 좋아요를 삭제합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @throws RestApiException 좋아요 삭제에 실패했을 때 발생
     */
    @Override
    public void deleteByUserIdAndPostId(Long userId, ObjectId postId) {
        if (repository.deleteByUserIdAndPostId(userId, postId) < 0) {
            throw new RestApiException(CommonExceptionCode.DELETE_FAIL_LIKE_IN_POST);
        }
    }

}
