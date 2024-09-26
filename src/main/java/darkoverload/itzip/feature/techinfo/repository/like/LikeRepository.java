package darkoverload.itzip.feature.techinfo.repository.like;

import darkoverload.itzip.feature.techinfo.model.document.LikeDocument;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * LikeDocument와 관련된 데이터베이스 작업을 처리하는 리포지토리.
 * 기본적인 MongoRepository 기능과 커스텀 메서드를 포함함.
 */
@ExcludeFromJpaRepositories
public interface LikeRepository extends MongoRepository<LikeDocument, ObjectId> {

    /**
     * 특정 유저가 특정 포스트에 대해 좋아요를 눌렀는지 여부를 확인.
     *
     * @param userId 유저 ID
     * @param postId 포스트 ID
     * @return 해당 유저가 해당 포스트에 대해 좋아요를 눌렀으면 true, 아니면 false
     */
    boolean existsByUserIdAndPostId(Long userId, ObjectId postId);

    /**
     * 특정 유저와 특정 포스트에 대한 좋아요를 삭제.
     *
     * @param userId 유저 ID
     * @param postId 포스트 ID
     */
    void deleteByUserIdAndPostId(Long userId, ObjectId postId);
}