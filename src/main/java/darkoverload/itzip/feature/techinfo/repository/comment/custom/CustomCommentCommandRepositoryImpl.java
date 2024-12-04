package darkoverload.itzip.feature.techinfo.repository.comment.custom;

import com.mongodb.client.result.UpdateResult;
import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 댓글 명령(수정, 삭제)을 처리하는 커스텀 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class CustomCommentCommandRepositoryImpl implements CustomCommentCommandRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * 댓글 내용을 업데이트합니다.
     *
     * @param commentId 업데이트할 댓글의 ID
     * @param userId    댓글 작성자의 ID
     * @param content   새로운 댓글 내용
     * @return 업데이트된 댓글의 수
     */
    @Override
    public long update(ObjectId commentId, Long userId, String content) {
        Query query = new Query(Criteria.where("_id").is(commentId).and("user_id").is(userId));

        Update update = new Update()
                .set("content", content)
                .set("modify_date", LocalDateTime.now());

        UpdateResult result = mongoTemplate.updateFirst(query, update, CommentDocument.class);
        return result.getModifiedCount();
    }

    /**
     * 댓글의 공개 상태를 업데이트합니다.
     *
     * @param commentId 업데이트할 댓글의 ID
     * @param userId    댓글 작성자의 ID
     * @param status    새로운 공개 상태
     * @return 업데이트된 댓글의 수
     */
    @Override
    public long update(ObjectId commentId, Long userId, boolean status) {
        Query query = new Query(Criteria.where("_id").is(commentId).and("user_id").is(userId));
        Update update = new Update().set("is_public", status);
        UpdateResult result = mongoTemplate.updateFirst(query, update, CommentDocument.class);
        return result.getModifiedCount();
    }

}