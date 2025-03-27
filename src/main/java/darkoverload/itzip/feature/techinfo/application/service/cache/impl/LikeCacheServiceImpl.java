package darkoverload.itzip.feature.techinfo.application.service.cache.impl;

import darkoverload.itzip.feature.techinfo.application.service.cache.LikeCacheService;
import darkoverload.itzip.feature.techinfo.application.service.command.ArticleCommandService;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.cache.LikeCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LikeCacheServiceImpl implements LikeCacheService {

    private static final String FLUSH_DELAY = "30000";

    private final LikeCacheRepository cacheRepository;

    private final ArticleCommandService articleCommandService;

    @Async
    @Override
    public void merge(final String articleId) {
        cacheRepository.merge(articleId, 1L);
    }

    @Async
    @Override
    public void subtract(final String articleId) {
        cacheRepository.merge(articleId, -1L);
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
