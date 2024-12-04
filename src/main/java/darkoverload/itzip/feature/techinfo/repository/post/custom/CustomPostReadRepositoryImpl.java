package darkoverload.itzip.feature.techinfo.repository.post.custom;

import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * MongoDB를 사용하여 포스트 조회 작업을 수행하는 커스텀 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class CustomPostReadRepositoryImpl implements CustomPostReadRepository {

    private static final List<String> POST_FIELDS = List.of("post_id", "title", "create_date");
    private static final String COLLECTION_NAME = "posts";

    private final MongoTemplate mongoTemplate;

    /**
     * 포스트 ID로 공개된 포스트를 조회합니다.
     *
     * @param id 포스트 ID
     * @return Optional<PostDocument>
     */
    @Override
    public Optional<PostDocument> findByPostId(ObjectId id) {
        Criteria criteria = Criteria.where("_id").is(id).and("is_public").is(true);
        return Optional.ofNullable(
                mongoTemplate.findOne(Query.query(criteria), PostDocument.class)
        );
    }

    /**
     * 모든 공개 포스트를 페이징하여 조회합니다.
     *
     * @param pageable 페이징 정보
     * @return Page<PostDocument>
     */
    @Override
    public Page<PostDocument> findAll(Pageable pageable) {
        return queryPostsWithCriteria(
                Criteria.where("is_public").is(true), pageable, true
        );
    }

    /**
     * 특정 블로그의 공개 포스트를 페이징하여 조회합니다.
     *
     * @param blogId   블로그 ID
     * @param pageable 페이징 정보
     * @return Page<PostDocument>
     */
    @Override
    public Page<PostDocument> findPostsByBlogId(Long blogId, Pageable pageable) {
        return queryPostsWithCriteria(
                Criteria.where("blog_id").is(blogId).and("is_public").is(true), pageable, true
        );
    }

    /**
     * 특정 카테고리의 공개 포스트를 페이징하여 조회합니다.
     *
     * @param categoryId 카테고리 ID
     * @param pageable   페이징 정보
     * @return Page<PostDocument>
     */
    @Override
    public Page<PostDocument> findPostsByCategoryId(ObjectId categoryId, Pageable pageable) {
        return queryPostsWithCriteria(
                Criteria.where("category_id").is(categoryId).and("is_public").is(true), pageable, true
        );
    }

    /**
     * 주어진 조건에 맞는 포스트를 페이징하여 조회합니다.
     *
     * @param criteria      조회 조건
     * @param pageable      페이징 정보
     * @param includeBlogId 블로그 ID 포함 여부
     * @return Page<PostDocument>
     */
    private Page<PostDocument> queryPostsWithCriteria(Criteria criteria, Pageable pageable, boolean includeBlogId) {
        Aggregation aggregation = newAggregation(
                match(criteria),
                createProjectionWithBlogId(includeBlogId),
                sort(pageable.getSort()),
                skip(pageable.getOffset()),
                limit(pageable.getPageSize())
        );

        List<PostDocument> posts = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, PostDocument.class)
                .getMappedResults();

        long total = mongoTemplate.count(Query.query(criteria), PostDocument.class);
        return new PageImpl<>(posts, pageable, total);
    }

    /**
     * 블로그 ID 포함 여부에 따른 프로젝션 연산을 생성합니다.
     *
     * @param includeBlogId 블로그 ID 포함 여부
     * @return ProjectionOperation
     */
    private ProjectionOperation createProjectionWithBlogId(boolean includeBlogId) {
        ProjectionOperation projectionOperation = project(
                "_id", "category_id", "title", "like_count", "create_date", "thumbnail_image_path")
                .and(aggregationOperationContext -> new Document("$substrCP", List.of("$content", 0, 300)))
                .as("content");

        if (includeBlogId) {
            projectionOperation = projectionOperation.and("blog_id").as("blog_id");
        }

        return projectionOperation;
    }

    /**
     * 특정 날짜 범위의 포스트를 조회합니다.
     *
     * @param blogId     블로그 ID
     * @param createDate 기준 날짜
     * @param limit      조회할 포스트 수
     * @return List<PostDocument>
     */
    @Override
    public List<PostDocument> findPostsByDateRange(Long blogId, LocalDateTime createDate, int limit) {
        Criteria previousCriteria = buildDateRangeCriteria(blogId, createDate, true);
        Criteria nextCriteria = buildDateRangeCriteria(blogId, createDate, false);

        List<PostDocument> previousPosts = queryPostsWithField(previousCriteria, Sort.Direction.DESC, limit);
        List<PostDocument> nextPosts = queryPostsWithField(nextCriteria, Sort.Direction.ASC, limit);

        return mergePosts(previousPosts, nextPosts, 2);
    }

    /**
     * 날짜 범위 조회를 위한 Criteria 를 생성합니다.
     *
     * @param blogId     블로그 ID
     * @param createDate 기준 날짜
     * @param isPrevious 이전 포스트 조회 여부
     * @return Criteria
     */
    private Criteria buildDateRangeCriteria(Long blogId, LocalDateTime createDate, boolean isPrevious) {
        Criteria criteria = Criteria.where("blog_id").is(blogId).and("is_public").is(true);
        return isPrevious ? criteria.and("create_date").lt(createDate) : criteria.and("create_date").gt(createDate);
    }

    /**
     * 주어진 조건에 맞는 포스트를 필드를 지정하여 조회합니다.
     *
     * @param criteria  조회 조건
     * @param direction 정렬 방향
     * @param limit     조회할 포스트 수
     * @return List<PostDocument>
     */
    private List<PostDocument> queryPostsWithField(Criteria criteria, Sort.Direction direction, int limit) {
        Query query = new Query(criteria)
                .with(Sort.by(direction, "create_date"))
                .limit(limit);

        POST_FIELDS.forEach(query.fields()::include);
        return mongoTemplate.find(query, PostDocument.class);
    }

    /**
     * 이전 포스트와 다음 포스트를 병합합니다.
     *
     * @param previousPosts   이전 포스트 목록
     * @param nextPosts       다음 포스트 목록
     * @param maxPostsToMerge 병합할 최대 포스트 수
     * @return List<PostDocument>
     */
    private List<PostDocument> mergePosts(List<PostDocument> previousPosts, List<PostDocument> nextPosts,
                                          int maxPostsToMerge) {
        if (!previousPosts.isEmpty() && !nextPosts.isEmpty()) {
            return Stream.concat(
                    nextPosts.stream().limit(maxPostsToMerge),
                    previousPosts.stream().limit(maxPostsToMerge)
            ).toList();
        }

        if (!previousPosts.isEmpty()) {
            return previousPosts;
        }

        return nextPosts;
    }

    /**
     * 특정 블로그의 연간 포스트 통계를 조회합니다.
     *
     * @param blogId 블로그 ID
     * @return List<YearlyPostStats>
     */
    @Override
    public List<YearlyPostStats> findYearlyPostStatsByBlogId(Long blogId) {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);

        Aggregation aggregation = newAggregation(
                match(new Criteria("create_date").gte(oneYearAgo)
                        .and("is_public").is(true)
                        .and("blog_id").is(blogId)),
                project()
                        .andExpression("year(create_date)").as("year")
                        .andExpression("month(create_date)").as("month")
                        .andExpression("1 + floor((dayOfMonth(create_date) - 1) / 7)").as("week"),
                group("year", "month", "week")
                        .count().as("postCount"),
                group("year", "month")
                        .push(new Document("week", "$_id.week").append("postCount", "$postCount")).as("weeks"),
                group("year")
                        .push(new Document("month", "$_id.month").append("weeks", "$weeks")).as("months"),
                project()
                        .and("_id").as("year")
                        .and("months").as("months"),
                sort(Sort.by(Sort.Direction.DESC, "year"))
        );

        AggregationResults<YearlyPostStats> results = mongoTemplate.aggregate(aggregation, "posts",
                YearlyPostStats.class);
        return results.getMappedResults();
    }

}
