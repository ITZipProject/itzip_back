package darkoverload.itzip.feature.techinfo.application.service.cache.impl;

import darkoverload.itzip.feature.techinfo.application.service.cache.ViewCacheService;
import darkoverload.itzip.feature.techinfo.application.service.command.ArticleCommandService;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.cache.ViewCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ViewCacheServiceImpl implements ViewCacheService {

    private static final String FLUSH_DELAY = "30000";

    private final ViewCacheRepository viewCacheRepository;

    private final ArticleCommandService articleCommandService;

    @Async
    @Retryable(
            value = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Override
    public void merge(final ObjectId articleId) {
        viewCacheRepository.merge(articleId, 1L);
    }

    @Recover
    public void recoverMerge(final Exception e, final ObjectId articleId) {
        log.error("뷰 캐시 병합 재시도 실패: {}", articleId);
    }

    @Scheduled(fixedDelayString = FLUSH_DELAY)
    @Override
    public void flush() {
        final Map<ObjectId, Long> batch = viewCacheRepository.retrieveAll();
        if (batch.isEmpty()) {
            return;
        }
        viewCacheRepository.clear();
        batch.forEach(articleCommandService::updateViewCount);
    }

}
