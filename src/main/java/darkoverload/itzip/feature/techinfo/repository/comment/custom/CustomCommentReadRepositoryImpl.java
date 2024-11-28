package darkoverload.itzip.feature.techinfo.repository.comment.custom;

import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 댓글 조회를 위한 커스텀 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class CustomCommentReadRepositoryImpl implements CustomCommentReadRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * 특정 포스트의 공개 댓글을 페이징하여 조회합니다.
     *
     * @param id       게시글 ID
     * @param pageable 페이징 정보
     * @return Page<CommentDocument>
     */
    @Override
    public Page<CommentDocument> findCommentsByPostId(Object id, Pageable pageable) {
        Query query = new Query(Criteria
                .where("post_id").is(id)
                .and("is_public").is(true))
                .with(pageable);

        query.fields()
                .exclude("post_id")
                .exclude("is_public");

        List<CommentDocument> comments = mongoTemplate.find(query, CommentDocument.class);

        long total = mongoTemplate.count(query, CommentDocument.class);

        return new PageImpl<>(comments, pageable, total);
    }

}
