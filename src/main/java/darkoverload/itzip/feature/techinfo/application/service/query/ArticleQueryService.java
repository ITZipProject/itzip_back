package darkoverload.itzip.feature.techinfo.application.service.query;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.ui.payload.response.ArticleResponse;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom.impl.YearlyArticleStatistics;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleQueryService {

    boolean existsById(String id);

    ArticleResponse getArticleById(CustomUserDetails userDetails, String id);

    Page<ArticleResponse> getArticlesPreviewByType(String articleType, int page, int size, String sortType);

    Page<ArticleResponse> getArticlesPreviewByAuthor(String nickname, int page, int size, String sortType);

    List<YearlyArticleStatistics> getYearlyArticleStatisticsByBlogId(Long blogId);

    List<ArticleResponse> getAdjacentArticles(Long blogId, String articleType, LocalDateTime createdAt);

}
