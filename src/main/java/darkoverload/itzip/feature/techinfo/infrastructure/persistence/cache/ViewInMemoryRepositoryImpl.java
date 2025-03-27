package darkoverload.itzip.feature.techinfo.infrastructure.persistence.cache;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class ViewInMemoryRepositoryImpl implements ViewCacheRepository {

    private final ConcurrentMap<ObjectId, Long> caches = new ConcurrentHashMap<>();

    @Override
    public void merge(final ObjectId articleId, final long value) {
        caches.merge(articleId, value, Long::sum);
    }

    @Override
    public Map<ObjectId, Long> retrieveAll() {
        return new HashMap<>(caches);
    }

    @Override
    public void clear() {
        caches.clear();
    }

}
