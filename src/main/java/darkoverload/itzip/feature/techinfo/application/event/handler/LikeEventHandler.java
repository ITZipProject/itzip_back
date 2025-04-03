package darkoverload.itzip.feature.techinfo.application.event.handler;

import darkoverload.itzip.feature.techinfo.application.event.payload.LikeCancelledEvent;
import darkoverload.itzip.feature.techinfo.application.event.payload.LikedEvent;
import darkoverload.itzip.feature.techinfo.application.service.cache.LikeCacheService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 좋아요 및 취소 이벤트를 수행하는 핸들러입니다.
 * 트랜잭션 커밋 이후에 실행됩니다.
 */
@Component
public class LikeEventHandler {

    private final LikeCacheService cacheService;

    public LikeEventHandler(final LikeCacheService likeCacheService) {
        this.cacheService = likeCacheService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleArticleLikedEvent(final LikedEvent event) {
        cacheService.merge(event.articleId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleArticleLikeCancelledEvent(final LikeCancelledEvent event) {
        cacheService.subtract(event.articleId());
    }

}
