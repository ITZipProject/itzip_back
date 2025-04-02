package darkoverload.itzip.feature.techinfo.application.event.handler;

import darkoverload.itzip.feature.techinfo.application.event.payload.ArticleViewedEvent;
import darkoverload.itzip.feature.techinfo.application.service.cache.ViewCacheService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ViewEventHandler {

    private final ViewCacheService cacheService;

    public ViewEventHandler(final ViewCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @EventListener
    public void handleArticleViewedEvent(final ArticleViewedEvent event) {
        cacheService.merge(event.articleId());
    }

}
