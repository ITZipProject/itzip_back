package darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom.impl;

import darkoverload.itzip.feature.techinfo.domain.entity.Article;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom.ArticleCustomRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    private static final String FIELD_ID = "_id";
    private static final String FIELD_LIKES_COUNT = "likes_count";
    private static final String FIELD_VIEW_COUNT = "view_count";
    private static final String FIELD_CREATED_AT = "created_at";
    private static final String ARTICLE_COLLECTION = "articles";

    private final MongoTemplate template;

    public ArticleCustomRepositoryImpl(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public List<YearlyArticleStatistics> findArticleYearlyStatisticsByBlogId(Long blogId) {
        final LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);

        final MatchOperation matchStage = Aggregation.match(
                Criteria.where("blog_id").is(blogId)
                        .and(FIELD_CREATED_AT).gte(oneYearAgo)
                        .and("is_displayed").is(true)
        );

        final ProjectionOperation projectionStage = Aggregation.project()
                .andExpression("year(created_at)").as("year")
                .andExpression("month(created_at)").as("month")
                .andExpression("1 + floor((dayOfMonth(created_at) - 1) / 7)").as("week");

        final GroupOperation groupStage = Aggregation.group("year", "month", "week")
                .count().as("article_count");

        final GroupOperation monthGroupStage = Aggregation.group("_id.year", "_id.month")
                .push(new Document("week", "$_id.week").append("articleCount", "$article_count"))
                .as("weeks");

        final GroupOperation yearGroupStage = Aggregation.group("year")
                .push(new Document("month", "$_id.month").append("weeks", "$weeks"))
                .as("months");

        final AggregationOperation sortWeeks = context -> new Document("$set",
                new Document("months",
                        new Document("$map",
                                new Document("input", "$months")
                                        .append("as", "monthDoc")
                                        .append("in", new Document("$mergeObjects", Arrays.asList(
                                                // 원본 monthDoc
                                                "$$monthDoc",
                                                // 새로운 weeks 필드(주차 내림차순)
                                                new Document("weeks",
                                                        new Document("$sortArray",
                                                                new Document("input", "$$monthDoc.weeks")
                                                                        .append("sortBy", new Document("week", -1))
                                                        )
                                                )
                                        )))
                        )
                )
        );

        final AggregationOperation sortMonths = context -> new Document("$set",
                new Document("months",
                        new Document("$sortArray",
                                new Document("input", "$months")
                                        .append("sortBy", new Document("month", -1))
                        )
                )
        );

        final ProjectionOperation finalProjectionStage = Aggregation.project()
                .and(FIELD_ID).as("year")
                .and("months").as("months");

        final SortOperation sortYear = Aggregation.sort(Sort.by(Sort.Direction.DESC, "year"));

        final Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                projectionStage,
                groupStage,
                monthGroupStage,
                yearGroupStage,
                sortWeeks,
                sortMonths,
                finalProjectionStage,
                sortYear
        );

        final AggregationResults<YearlyArticleStatistics> results = template.aggregate(aggregation, ARTICLE_COLLECTION, YearlyArticleStatistics.class);
        return results.getMappedResults();
    }

    @Override
    public void updateViewCount(ObjectId id, long count) {
        final Query query = new Query(Criteria.where(FIELD_ID).is(id));
        final Update update = new Update().inc(FIELD_VIEW_COUNT, count);
        template.updateFirst(query, update, Article.class);
    }

    @Override
    public void updateLikesCount(ObjectId id, long count) {
        final Query query = new Query(Criteria.where(FIELD_ID).is(id));
        final Update update = new Update().inc(FIELD_LIKES_COUNT, count);
        template.updateFirst(query, update, Article.class);
    }

}
