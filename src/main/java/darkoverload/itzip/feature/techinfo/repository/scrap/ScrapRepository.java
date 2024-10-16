package darkoverload.itzip.feature.techinfo.repository.scrap;

import darkoverload.itzip.feature.techinfo.model.document.ScrapDocument;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

@ExcludeFromJpaRepositories
public interface ScrapRepository extends MongoRepository<ScrapDocument, ObjectId> {

    void deleteByUserIdAndPostId(Long userId, ObjectId postId);

    boolean existsByUserIdAndPostId(Long userId, ObjectId postId);
}