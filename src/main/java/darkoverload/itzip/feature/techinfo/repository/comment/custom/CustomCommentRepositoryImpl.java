package darkoverload.itzip.feature.techinfo.repository.comment.custom;

import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;

import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    private final MongoTemplate mongoTemplate;

    // 댓글 내용을 업데이트
    @Override
    public boolean updateComment(ObjectId commentId, String content) {
        Query query = new Query(Criteria.where("_id").is(commentId)); // 댓글 ID로 필터링

        Update update = new Update().set("content", content); // 댓글 내용 업데이트

        CommentDocument savedComment = mongoTemplate.findAndModify(query, update, CommentDocument.class); // 업데이트 실행

        return savedComment != null; // 업데이트 성공 여부 반환
    }

    // 댓글 공개 여부를 업데이트
    @Override
    public boolean updateCommentVisibility(ObjectId commentId, boolean isPublic) {
        Query query = new Query(Criteria.where("_id").is(commentId)); // 댓글 ID로 필터링

        Update update = new Update().set("is_public", isPublic); // 공개 여부 업데이트

        CommentDocument savedComment = mongoTemplate.findAndModify(query, update, CommentDocument.class); // 업데이트 실행

        return savedComment != null; // 업데이트 성공 여부 반환
    }

    // 포스트 ID로 댓글을 페이지네이션하여 조회
    @Override
    public Page<CommentDocument> findCommentsByPostId(ObjectId postId, Pageable pageable) {
        Query query = new Query(Criteria.where("post_id").is(postId).and("is_public").is(true)) // 포스트 ID와 공개 여부로 필터링
                .with(pageable); // 페이지네이션 적용

        query.fields().exclude("is_public").exclude("post_id"); // is_public과 post_id 필드를 제외

        List<CommentDocument> commentDocuments = mongoTemplate.find(query, CommentDocument.class); // 필터링된 결과 조회

        long total = mongoTemplate.count(Query.query(Criteria.where("post_id").is(postId).and("is_public").is(true)), CommentDocument.class); // 총 댓글 수 조회

        return new PageImpl<>(commentDocuments, pageable, total); // 페이지네이션된 결과 반환
    }
}