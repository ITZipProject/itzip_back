package darkoverload.itzip.feature.techinfo.application.service.query.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.event.payload.ArticleViewedEvent;
import darkoverload.itzip.feature.techinfo.application.generator.PageableGenerator;
import darkoverload.itzip.feature.techinfo.ui.payload.response.ArticleResponse;
import darkoverload.itzip.feature.techinfo.application.service.query.ArticleQueryService;
import darkoverload.itzip.feature.techinfo.application.service.query.BlogQueryService;
import darkoverload.itzip.feature.techinfo.application.service.query.LikeQueryService;
import darkoverload.itzip.feature.techinfo.application.service.query.ScrapQueryService;
import darkoverload.itzip.feature.techinfo.application.type.SortType;
import darkoverload.itzip.feature.techinfo.domain.entity.Article;
import darkoverload.itzip.feature.techinfo.domain.entity.ArticleType;
import darkoverload.itzip.feature.techinfo.domain.entity.Blog;
import darkoverload.itzip.feature.techinfo.domain.projection.ArticlePreview;
import darkoverload.itzip.feature.techinfo.domain.projection.ArticleSummary;
import darkoverload.itzip.feature.techinfo.domain.repository.ArticleRepository;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom.impl.YearlyArticleStatistics;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CacheConfig(
        cacheManager = "caffeineCacheManager",
        cacheNames = "articlesPreview"
)
@Service
public class ArticleQueryServiceImpl implements ArticleQueryService {

    private final ArticleRepository articleRepository;

    private final BlogQueryService blogQueryService;
    private final LikeQueryService likeQueryService;
    private final ScrapQueryService scrapQueryService;

    private final Executor asyncExecutor;
    private final ApplicationEventPublisher eventPublisher;

    public ArticleQueryServiceImpl(
            final ArticleRepository articleRepository,
            final BlogQueryService blogQueryService,
            final LikeQueryService likeQueryService,
            final ScrapQueryService scrapQueryService,
            @Qualifier("asyncExecutor") final Executor asyncExecutor,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.articleRepository = articleRepository;
        this.blogQueryService = blogQueryService;
        this.likeQueryService = likeQueryService;
        this.scrapQueryService = scrapQueryService;
        this.asyncExecutor = asyncExecutor;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean existsById(final String id) {
        return articleRepository.existsById(new ObjectId(id));
    }

    @Override
    public ArticleResponse getArticleById(final CustomUserDetails userDetails, final String id) {
        final CompletableFuture<Boolean> isLikedFuture;
        final CompletableFuture<Boolean> isScrappedFuture;

        if (Objects.nonNull(userDetails)) {
            isLikedFuture = CompletableFuture.supplyAsync(() ->
                    likeQueryService.existsByUserNicknameAndArticleId(userDetails.getUserNickname(), id),
                    asyncExecutor
            );
            isScrappedFuture = CompletableFuture.supplyAsync(() ->
                    scrapQueryService.existsByUserNicknameAndArticleId(userDetails.getUserNickname(), id),
                    asyncExecutor
            );
        } else {
            isLikedFuture = CompletableFuture.completedFuture(false);
            isScrappedFuture = CompletableFuture.completedFuture(false);
        }

        final Article article = articleRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.ARTICLE_NOT_FOUND));
        final Blog blog = blogQueryService.getBlogById(article.getBlogId());

        eventPublisher.publishEvent(new ArticleViewedEvent(article.getId()));

        final boolean isLiked = isLikedFuture.join();
        final boolean isScrapped = isScrappedFuture.join();

        return ArticleResponse.from(blog, article, isLiked, isScrapped);
    }

    @Cacheable(
            key = "#articleType + '_' + #page + '_' + #size + '_' + #sortType",
            condition = "#page <= 5"
    )
    @Override
    public Page<ArticleResponse> getArticlesPreviewByType(final String articleType, final int page, final int size, final String sortType) {
        final Pageable pageable = PageableGenerator.generate(page, size, SortType.from(sortType));

        final Page<ArticlePreview> articles = (articleType == null || articleType.isBlank())
                ? articleRepository.findAllByDisplayedIsTrue(pageable)
                : articleRepository.findAllByTypeAndDisplayedIsTrue(ArticleType.from(articleType), pageable);

        if (articles.isEmpty()) {
            throw new RestApiException(CommonExceptionCode.ARTICLE_NOT_FOUND);
        }

        final Set<Long> blogIds = articles.stream()
                .map(ArticlePreview::getBlogId)
                .collect(Collectors.toSet());
        final Map<Long, Blog> blogMap = blogQueryService.getBlogMapByIds(blogIds);

        return articles.map(article -> {
            final Blog blog = blogMap.get(article.getBlogId());
            return ArticleResponse.previewFrom(blog, article);
        });
    }

    @Override
    public Page<ArticleResponse> getArticlesPreviewByAuthor(final String nickname, final int page, final int size, final String sortType) {
        final Long blogId = blogQueryService.getBlogIdByUserNickname(nickname);
        final Pageable pageable = PageableGenerator.generate(page, size, SortType.from(sortType));
        final Page<ArticlePreview> articles = articleRepository.findAllByBlogIdAndDisplayedIsTrue(blogId, pageable);
        if (articles.isEmpty()) {
            throw new RestApiException(CommonExceptionCode.ARTICLE_NOT_FOUND);
        }
        return articles.map(ArticleResponse::previewFrom);
    }

    @Override
    public List<YearlyArticleStatistics> getYearlyArticleStatisticsByBlogId(final Long blogId) {
        return articleRepository.findArticleYearlyStatisticsByBlogId(blogId);
    }

    @NonNull
    @Override
    public List<ArticleResponse> getAdjacentArticles(final Long blogId, final String articleType, final LocalDateTime createdAt) {
        final ArticleType type = ArticleType.from(articleType);
        final Pageable limit = PageRequest.of(0, 2);

        final List<ArticleSummary> nextArticleSummaries = articleRepository
                .findNextArticlesByBlogIdAndDisplayedIsTrue(blogId, type, createdAt, limit);

        final List<ArticleSummary> previousArticlesSummaries = articleRepository
                .findPreviousArticlesByBlogIdAndDisplayedIsTrue(blogId, type, createdAt, limit);

        final List<ArticleResponse> adjacentArticles = Stream.concat(
                nextArticleSummaries
                        .stream()
                        .map(ArticleResponse::summaryFrom),
                previousArticlesSummaries
                        .stream()
                        .map(ArticleResponse::summaryFrom)
        ).toList();

        return adjacentArticles;
    }

}
