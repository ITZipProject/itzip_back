package darkoverload.itzip.feature.techinfo.application.service.cache.impl;

import darkoverload.itzip.feature.techinfo.application.service.cache.LikeCacheService;
import darkoverload.itzip.feature.techinfo.application.service.command.ArticleCommandService;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.cache.LikeCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class LikeCacheServiceImpl implements LikeCacheService {

    private static final String FLUSH_DELAY = "30000";

    private final LikeCacheRepository cacheRepository;

    private final ArticleCommandService articleCommandService;

    @Async
    @Retryable(
            value = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Override
    public void merge(final String articleId) {
        cacheRepository.merge(articleId, 1L);
    }

    @Recover
    public void recoverMerge(final Exception e, final String articleId) {
        log.error("좋아요 병합 재시도 실패: {}", articleId);
    }

    @Async
    @Retryable(
            value = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Override
    public void subtract(final String articleId) {
        cacheRepository.merge(articleId, -1L);
    }

    @Recover
    public void recoverSubtract(final Exception e, final String articleId) {
        log.error("좋아요 감소 재시도 실패: {}", articleId);
    }

    @Scheduled(fixedDelayString = FLUSH_DELAY)
    public void flush() {
        final Map<String, Long> batch = cacheRepository.retrieveAll();
        if (batch.isEmpty()) {
            return;
        }
        cacheRepository.clear();
        batch.forEach(articleCommandService::updateLikesCount);
    }

}
