package darkoverload.itzip.feature.techinfo.repository.post.custom;

import com.mongodb.client.result.UpdateResult;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * MongoDB를 사용하여 포스트 정보를 수정하는 커스텀 레포지토리 구현 클래스.
 */
@RequiredArgsConstructor
public class CustomPostCommandRepositoryImpl implements CustomPostCommandRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * 포스트의 상세 정보를 업데이트합니다.
     *
     * @param postId             포스트 ID
     * @param categoryId         카테고리 ID
     * @param title              제목
     * @param content            내용
     * @param thumbnailImagePath 썸네일 이미지 URL
     * @param contentImagePaths  본문 이미지 URL 목록
     * @return Optional<PostDocument>
     */
    @Override
    public Optional<PostDocument> update(ObjectId postId, ObjectId categoryId, String title, String content,
                                        String thumbnailImagePath, List<String> contentImagePaths) {
        Query query = new Query(Criteria.where("_id").is(postId));

        Update update = new Update()
                .set("category_id", categoryId)
                .set("title", title)
                .set("content", content)
                .set("thumbnail_image_path", thumbnailImagePath)
                .set("content_image_paths", contentImagePaths)
                .set("modify_date", LocalDateTime.now());

        FindAndModifyOptions options = FindAndModifyOptions.options()
                .returnNew(true)
                .upsert(false);

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, options, PostDocument.class));
    }

    /**
     * 포스트의 공개 상태를 업데이트합니다.
     *
     * @param postId 포스트 ID
     * @param status 새로운 공개 상태
     * @return Optional<PostDocument>
     */
    @Override
    public Optional<PostDocument> update(ObjectId postId, boolean status) {
        Query query = new Query(Criteria.where("_id").is(postId));
        Update update = new Update().set("is_public", status);

        FindAndModifyOptions options = FindAndModifyOptions.options()
                .returnNew(true)
                .upsert(false);

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, options, PostDocument.class));
    }

    /**
     * 포스트의 특정 필드 값을 증가시킵니다.
     *
     * @param postId    포스트 ID
     * @param fieldName 업데이트할 필드 이름
     * @param value     증가시킬 값
     * @return PostDocument
     */
    @Override
    public Optional<PostDocument> updateFieldWithValue(ObjectId postId, String fieldName, int value) {
        Query query = new Query(Criteria.where("_id").is(postId));
        Update update = new Update().inc(fieldName, value);

        FindAndModifyOptions options = FindAndModifyOptions.options()
                .returnNew(true)
                .upsert(false);

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, options, PostDocument.class));
    }

}
