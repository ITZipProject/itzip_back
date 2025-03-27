package darkoverload.itzip.feature.techinfo.application.service.cache.impl;

import darkoverload.itzip.feature.techinfo.application.service.cache.ViewCacheService;
import darkoverload.itzip.feature.techinfo.application.service.command.ArticleCommandService;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.cache.ViewCacheRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ViewCacheServiceImpl implements ViewCacheService {

    private static final String FLUSH_DELAY = "30000";

    private final ViewCacheRepository viewCacheRepository;

    private final ArticleCommandService articleCommandService;

    @Async
    @Override
    public void merge(final ObjectId articleId) {
        viewCacheRepository.merge(articleId, 1L);
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
