package darkoverload.itzip.feature.techinfo.repository.comment;

import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import darkoverload.itzip.feature.techinfo.repository.comment.custom.CustomCommentRepository;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * CommentDocument와 관련된 데이터베이스 작업을 처리하는 리포지토리.
 * 기본적인 MongoRepository 기능과 커스텀 메서드를 포함함.
 */
@ExcludeFromJpaRepositories
public interface CommentRepository extends MongoRepository<CommentDocument, ObjectId>, CustomCommentRepository {
}