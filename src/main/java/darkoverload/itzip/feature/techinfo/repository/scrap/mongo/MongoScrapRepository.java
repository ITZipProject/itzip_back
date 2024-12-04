package darkoverload.itzip.feature.techinfo.repository.scrap.mongo;

import darkoverload.itzip.feature.techinfo.model.document.ScrapDocument;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

@ExcludeFromJpaRepositories
public interface MongoScrapRepository extends MongoRepository<ScrapDocument, ObjectId> {

    /**
     * 특정 사용자가 특정 포스트를 스크랩했는지 확인합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 스크랩이 존재하면 true, 그렇지 않으면 false
     */
    boolean existsByUserIdAndPostId(Long userId, ObjectId postId);

    /**
     * 특정 사용자의 특정 포스트에 대한 스크랩을 삭제합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 삭제된 스크랩의 수
     */
    long deleteByUserIdAndPostId(Long userId, ObjectId postId);

}
