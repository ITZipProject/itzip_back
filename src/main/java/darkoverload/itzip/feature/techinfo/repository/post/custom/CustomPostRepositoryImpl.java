package darkoverload.itzip.feature.techinfo.repository.post.custom;

import darkoverload.itzip.feature.techinfo.dto.post.year.YearlyPostDto;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import org.bson.types.ObjectId;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Slf4j
@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {

    private final MongoTemplate mongoTemplate;

    // 포스트를 부분 업데이트하는 메서드
    @Override
    public boolean updatePost(ObjectId postId, ObjectId categoryId, String title, String content, String thumbnailImagePath, List<String> contentImagePaths) {

        Query query = new Query(Criteria.where("_id").is(postId)); // 포스트 조회 조건 설정

        // 업데이트할 필드 설정
        Update update = new Update()
                .set("category_id", categoryId) // 카테고리 ID
                .set("title", title) // 제목
                .set("content", content) // 본문 내용
                .set("thumbnail_image_path", thumbnailImagePath) // 썸네일 이미지 경로
                .set("content_image_paths", contentImagePaths); // 본문 이미지 경로

        // 업데이트 실행 및 결과 반환
        PostDocument savedPost = mongoTemplate.findAndModify(query, update, PostDocument.class);

        return savedPost != null; // 업데이트 성공 여부 반환
    }

    // 포스트의 좋아요 수를 증가시키는 메서드
    @Override
    public void updateLikeCount(ObjectId postId, int increment) {

        Query query = new Query(Criteria.where("_id").is(postId)); // 포스트 조회 조건 설정

        Update update = new Update().inc("like_count", increment); // 좋아요 수 증가

        mongoTemplate.updateFirst(query, update, PostDocument.class); // 업데이트 실행
    }

    // 포스트의 조회수를 1 증가시키는 메서드
    @Override
    public void updateViewCount(ObjectId postId) {

        Query query = new Query(Criteria.where("_id").is(postId)); // 포스트 조회 조건 설정

        Update update = new Update().inc("view_count", 1); // 조회수 증가

        mongoTemplate.updateFirst(query, update, PostDocument.class); // 업데이트 실행
    }

    // 포스트 공개 여부를 업데이트하는 메서드
    @Override
    public boolean updatePostVisibility(ObjectId postId, boolean isPublic) {

        Query query = new Query(Criteria.where("_id").is(postId)); // 포스트 조회 조건 설정

        Update update = new Update().set("is_public", isPublic); // 공개 여부 업데이트

        // 업데이트 실행 및 결과 반환
        PostDocument savedPost = mongoTemplate.findAndModify(query, update, PostDocument.class);

        return savedPost != null; // 업데이트 성공 여부 반환
    }

    // 모든 공개된 포스트를 조회하는 메서드
    @Override
    public Page<PostDocument> findAllPosts(Pageable pageable) {

        Criteria criteria = Criteria.where("is_public").is(true); // 공개된 포스트 조건 추가

        return executeQueryWithCriteria(criteria, pageable, true);
    }

    // 카테고리 ID로 포스트 조회
    @Override
    public Page<PostDocument> findPostsByCategoryId(ObjectId categoryId, Pageable pageable) {

        Criteria criteria = Criteria.where("category_id").is(categoryId).and("is_public").is(true); // 카테고리와 공개된 포스트 조건 추가

        return executeQueryWithCriteria(criteria, pageable, true); // 실행
    }

    // 회원 ID로 포스트 조회
    @Override
    public Page<PostDocument> findPostsByBlogId(Long blogId, Pageable pageable) {

        Criteria criteria = Criteria.where("blog_id").is(blogId).and("is_public").is(true); // 회원 ID와 공개 여부 조건 추가

        return executeQueryWithCriteria(criteria, pageable, false); // 실행
    }

    // 연간 포스트 데이터를 조회하는 메서드
    @Override
    public List<YearlyPostDto> findYearlyPostCounts(Long blogId) {

        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1); // 1년 전 날짜 설정

        // Aggregation 파이프라인 설정
        Aggregation aggregation = newAggregation(
                match(new Criteria("create_date").gte(oneYearAgo)
                        .and("is_public").is(true)
                        .and("blog_id").is(blogId)), // 1년 이내, 공개된 포스트 조회
                project()
                        .andExpression("year(create_date)").as("year") // 연도 추출
                        .andExpression("month(create_date)").as("month") // 월 추출
                        .andExpression("1 + floor((dayOfMonth(create_date) - 1) / 7)").as("week"), // 주차 계산
                group(fields().and("year").and("month").and("week"))
                        .count().as("postCount"), // 주차별 포스트 개수
                group("year", "month")
                        .push(new Document("week", "$_id.week").append("postCount", "$postCount")).as("weeks"), // 주차별 데이터 배열
                group("year")
                        .push(new Document("month", "$_id.month").append("weeks", "$weeks")).as("months"), // 월별 데이터 배열
                project()
                        .and("_id").as("year") // 연도를 id에서 year로 변경
                        .and("months").as("months"), // 월 데이터를 포함
                sort(Sort.by(Sort.Direction.DESC, "year")) // 연도 내림차순 정렬
        );

        // Aggregation 실행 및 결과 반환
        AggregationResults<YearlyPostDto> result = mongoTemplate.aggregate(aggregation, "posts", YearlyPostDto.class);

        return result.getMappedResults();
    }

    // 이전/이후 포스트를 조회하는 메서드
    @Override
    public List<PostDocument> findAdjacentPosts(Long userId, LocalDateTime createDate, int limit) {

        // 이전 포스트 조회 (작성일이 기준 날짜 이전)
        List<PostDocument> previousPosts = fetchPostsWithProjection(
                Criteria.where("blog_id").is(userId)
                        .and("create_date").lt(createDate) // 이전 포스트 조건
                        .and("is_public").is(true), // 공개된 포스트만
                Sort.Direction.DESC, limit,
                List.of("post_id", "title", "create_date") // 필요한 필드만 조회
        );

        // 이후 포스트 조회 (작성일이 기준 날짜 이후)
        List<PostDocument> nextPosts = fetchPostsWithProjection(
                Criteria.where("blog_id").is(userId)
                        .and("create_date").gt(createDate) // 이후 포스트 조건
                        .and("is_public").is(true),
                Sort.Direction.ASC, limit,
                List.of("post_id", "title", "create_date")
        );

        // 이전/이후 포스트가 모두 있을 경우 결합 후 반환
        if (!previousPosts.isEmpty() && !nextPosts.isEmpty()) {
            List<PostDocument> adjacentPosts = new ArrayList<>(nextPosts);
            adjacentPosts.addAll(previousPosts);
            return adjacentPosts;
        }

        // 이전 포스트가 없으면 이후 포스트만, 이후 포스트가 없으면 이전 포스트만 반환
        return previousPosts.isEmpty() ?
                nextPosts.stream().limit(4).toList() :
                previousPosts.stream().limit(4).toList();
    }

    // Criteria와 Projection을 사용해 포스트를 조회하는 메서드
    private List<PostDocument> fetchPostsWithProjection(Criteria criteria, Sort.Direction direction, int limit, List<String> fields) {

        Query query = new Query(criteria)
                .with(Sort.by(direction, "create_date")) // 작성일 기준으로 정렬
                .limit(limit); // 조회할 포스트 수 제한

        fields.forEach(field -> query.fields().include(field)); // 필요한 필드만 포함

        return mongoTemplate.find(query, PostDocument.class); // MongoDB에서 조회 실행
    }

    // 필터링 및 페이지네이션된 포스트 목록 조회
    private Page<PostDocument> executeQueryWithCriteria(Criteria criteria, Pageable pageable, boolean includeBlogId) {

        MatchOperation matchStage = match(criteria); // 필터링 기준 적용
        ProjectionOperation projectionStage = createProjectionOperation(includeBlogId); // 필드 선택 적용
        SortOperation sortStage = Aggregation.sort(pageable.getSort()); // 정렬 기준 적용

        // Aggregation 파이프라인 구성
        Aggregation aggregation = newAggregation(
                matchStage,
                projectionStage,
                sortStage,
                Aggregation.skip(pageable.getOffset()), // 페이징 오프셋
                Aggregation.limit(pageable.getPageSize()) // 페이징 크기
        );

        // MongoDB에서 Aggregation 실행
        List<PostDocument> postDocuments = mongoTemplate.aggregate(aggregation, "posts", PostDocument.class).getMappedResults();

        long total = mongoTemplate.count(Query.query(criteria), PostDocument.class); // 총 개수 계산

        return new PageImpl<>(postDocuments, pageable, total); // 페이지네이션된 결과 반환
    }

    // ProjectionOperation 생성 메서드, 특정 필드만 포함할지 여부에 따라 반환값 설정
    private ProjectionOperation createProjectionOperation(boolean includeBlogId) {

        ProjectionOperation projectionOperation = project("_id", "category_id", "title", "like_count", "create_date", "thumbnail_image_path")
                .and(aggregationOperationContext -> new Document("$substrCP", List.of("$content", 0, 300))) // 본문 내용 중 처음 300자만 포함
                .as("content"); // content 필드로 이름을 지정

        if (includeBlogId) {
            projectionOperation = projectionOperation.and("blog_id").as("blog_id"); // blog_id 필드 포함 여부에 따른 처리
        }

        return projectionOperation;
    }
}