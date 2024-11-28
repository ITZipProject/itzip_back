package darkoverload.itzip.feature.techinfo.repository.comment;

import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import darkoverload.itzip.feature.techinfo.repository.comment.custom.CustomCommentCommandRepository;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

@ExcludeFromJpaRepositories
public interface MongoCommentCommandRepository extends MongoRepository<CommentDocument, ObjectId>,
        CustomCommentCommandRepository {
}
