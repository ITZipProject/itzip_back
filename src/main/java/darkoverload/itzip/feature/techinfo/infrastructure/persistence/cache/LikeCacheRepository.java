package darkoverload.itzip.feature.techinfo.infrastructure.persistence.cache;

import java.util.Map;

public interface LikeCacheRepository {

    void merge(String articleId, long value);

    Map<String, Long> retrieveAll();

    void clear();

}
