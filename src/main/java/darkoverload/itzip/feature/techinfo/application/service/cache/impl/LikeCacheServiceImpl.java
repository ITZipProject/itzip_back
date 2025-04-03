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

/**
 * 좋아요 캐시 처리를 위한 서비스 구현체입니다.
 *
 * <p>
 *     좋아요 병합, 감소, 그리고 캐시 플러시 작업을 수행합니다.
 *     재시도 로직과 비동기 처리(@Async)를 적용하여 일시적인 오류에 대비합니다.
 * </p>
 */
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
