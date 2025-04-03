package darkoverload.itzip.feature.techinfo.infrastructure.persistence.cache;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class LikeInMemoryRepositoryImpl implements LikeCacheRepository {

    private final ConcurrentMap<String, Long> caches = new ConcurrentHashMap<>();

    @Override
    public void merge(final String articleId, final long value) {
        caches.merge(articleId, value, Long::sum);
    }

    @Override
    public Map<String, Long> retrieveAll() {
        return new HashMap<>(caches);
    }

    @Override
    public void clear() {
        caches.clear();
    }

}
