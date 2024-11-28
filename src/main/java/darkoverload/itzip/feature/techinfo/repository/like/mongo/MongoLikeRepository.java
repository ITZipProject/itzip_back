package darkoverload.itzip.feature.techinfo.repository.like.mongo;

import darkoverload.itzip.feature.techinfo.model.document.LikeDocument;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

@ExcludeFromJpaRepositories
public interface MongoLikeRepository extends MongoRepository<LikeDocument, ObjectId> {

    /**
     * 특정 사용자가 특정 포스트에 좋아요를 눌렀는지 확인합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 좋아요가 존재하면 true, 그렇지 않으면 false
     */
    boolean existsByUserIdAndPostId(Long userId, ObjectId postId);

    /**
     * 특정 사용자의 특정 포스트에 대한 좋아요를 삭제합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 삭제된 좋아요의 수
     */
    long deleteByUserIdAndPostId(Long userId, ObjectId postId);

}
