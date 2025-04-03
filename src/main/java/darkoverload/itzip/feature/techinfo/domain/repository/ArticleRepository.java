package darkoverload.itzip.feature.techinfo.domain.repository;

import darkoverload.itzip.feature.techinfo.domain.entity.Article;
import darkoverload.itzip.feature.techinfo.domain.projection.ArticlePreview;
import darkoverload.itzip.feature.techinfo.domain.entity.ArticleType;
import darkoverload.itzip.feature.techinfo.domain.projection.ArticleSummary;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom.impl.YearlyArticleStatistics;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Optional<Article> findById(ObjectId id);

    Optional<Article> findByIdAndBlogId(ObjectId id, Long blogId);

    Page<ArticlePreview> findAllByDisplayedIsTrue(Pageable pageable);

    Page<ArticlePreview> findAllByBlogIdAndDisplayedIsTrue(Long blogId, Pageable pageable);

    Page<ArticlePreview> findAllByTypeAndDisplayedIsTrue(ArticleType type, Pageable pageable);

    @Query(
            value = "{ 'blog_id': ?0, 'type': ?1, 'displayed': true, 'created_at': { $lt: ?2 } }",
            sort = "{ 'created_at': -1 }"
    )
    List<ArticleSummary> findPreviousArticlesByBlogIdAndDisplayedIsTrue(Long blogId, ArticleType type, LocalDateTime createdAt, Pageable pageable);

    @Query(
            value = "{ 'blog_id': ?0, 'type': ?1, 'displayed': true, 'created_at': { $gt: ?2 } }",
            sort = "{ 'created_at': 1 }"
    )
    List<ArticleSummary> findNextArticlesByBlogIdAndDisplayedIsTrue(Long blogId, ArticleType type, LocalDateTime createdAt, Pageable pageable);

    boolean existsById(ObjectId id);

    List<YearlyArticleStatistics> findArticleYearlyStatisticsByBlogId(Long blogId);

    void updateViewCount(ObjectId id, long count);

    void updateLikesCount(ObjectId id, long count);

    void deleteById(ObjectId id);

}
