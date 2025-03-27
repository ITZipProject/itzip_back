package darkoverload.itzip.feature.techinfo.application.service.command.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.event.payload.ArticleHiddenEvent;
import darkoverload.itzip.feature.techinfo.application.service.command.ArticleCommandService;
import darkoverload.itzip.feature.techinfo.application.service.query.BlogQueryService;
import darkoverload.itzip.feature.techinfo.domain.entity.Article;
import darkoverload.itzip.feature.techinfo.domain.repository.ArticleRepository;
import darkoverload.itzip.feature.techinfo.ui.payload.request.article.ArticleEditRequest;
import darkoverload.itzip.feature.techinfo.ui.payload.request.article.ArticleRegistrationRequest;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleCommandServiceImpl implements ArticleCommandService {

    private final ArticleRepository articleRepository;

    private final BlogQueryService blogQueryService;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public String create(final CustomUserDetails userDetails, final ArticleRegistrationRequest request) {
        this.checkUserDetails(userDetails);
        final Long blogId = blogQueryService.getBlogIdByUserNickname(userDetails.getUserNickname());
        final Article article = Article.create(
                blogId,
                request.type(),
                request.title(),
                request.content(),
                request.thumbnailImageUri()
        );
        articleRepository.save(article);
        return article.getId().toHexString();
    }

    @Override
    public void update(final CustomUserDetails userDetails, final ArticleEditRequest request) {
        this.checkUserDetails(userDetails);
        final Long blogId = blogQueryService.getBlogIdByUserNickname(userDetails.getUserNickname());
        final Article article = articleRepository.findByIdAndBlogId(new ObjectId(request.articleId()), blogId)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.ARTICLE_NOT_FOUND));
        final Article updatedArticle = article.update(
                request.type(),
                request.title(),
                request.content(),
                request.thumbnailImageUri()
        );
        articleRepository.save(updatedArticle);
    }

    @Override
    public void delete(final CustomUserDetails userDetails, final String articleId) {
        this.checkUserDetails(userDetails);
        final Long blogId = blogQueryService.getBlogIdByUserNickname(userDetails.getUserNickname());
        final Article article = articleRepository.findByIdAndBlogId(new ObjectId(articleId), blogId)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.ARTICLE_NOT_FOUND));
        article.hide();
        articleRepository.save(article);
        eventPublisher.publishEvent(new ArticleHiddenEvent(article.getId()));
    }

    @Override
    public void updateViewCount(final ObjectId id, final long count) {
        articleRepository.updateViewCount(id, count);
    }

    @Override
    public void updateLikesCount(final String id, final long count) {
        articleRepository.updateLikesCount(new ObjectId(id), count);
    }

    private void checkUserDetails(final CustomUserDetails userDetails) {
        if (Objects.isNull(userDetails)) {
            throw new RestApiException(CommonExceptionCode.UNAUTHORIZED);
        }
    }

}
