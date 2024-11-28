package darkoverload.itzip.feature.techinfo.repository.post;

import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.repository.post.custom.CustomPostCommandRepository;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

@ExcludeFromJpaRepositories
public interface MongoPostCommandRepository extends MongoRepository<PostDocument, ObjectId>,
        CustomPostCommandRepository {
}
