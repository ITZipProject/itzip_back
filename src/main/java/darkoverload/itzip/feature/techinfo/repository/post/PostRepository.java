package darkoverload.itzip.feature.techinfo.repository.post;

import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.repository.post.custom.CustomPostRepository;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * Post 관련 데이터베이스 접근을 위한 리포지토리.
 * 커스텀 메서드와 기본적인 MongoRepository 기능을 포함함.
 */
@ExcludeFromJpaRepositories
public interface PostRepository extends MongoRepository<PostDocument, ObjectId>, CustomPostRepository {

    /**
     * 특정 ID를 가진 공개된 포스트를 조회. 'is_public' 필드는 결과에서 제외됨.
     *
     * @param postId 조회할 포스트의 ID
     * @return 공개된 포스트를 담고 있는 Optional
     */
    @Query(value = "{ '_id': ?0, 'is_public': true }", fields = "{ 'is_public': 0 }")
    Optional<PostDocument> findByIdExcludingIsPublic(ObjectId postId);

    /**
     * 주어진 포스트 ID에 해당하는 공개된 포스트가 존재하는지 여부를 확인함.
     *
     * @param postId 확인할 포스트의 MongoDB ObjectId
     * @return 공개된 포스트가 존재하면 true, 존재하지 않으면 false
     */
    boolean existsByIdAndIsPublic(ObjectId postId);
}