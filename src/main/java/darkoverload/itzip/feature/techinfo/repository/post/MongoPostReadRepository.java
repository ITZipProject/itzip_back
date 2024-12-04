package darkoverload.itzip.feature.techinfo.repository.post;

import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.repository.post.custom.CustomPostReadRepository;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

@ExcludeFromJpaRepositories
public interface MongoPostReadRepository extends MongoRepository<PostDocument, ObjectId>, CustomPostReadRepository {

    boolean existsByIdAndIsPublicTrue(ObjectId postId);

    long countByIsPublicTrue();

}
