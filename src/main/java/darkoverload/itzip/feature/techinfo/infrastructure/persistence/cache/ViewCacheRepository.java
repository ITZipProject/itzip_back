package darkoverload.itzip.feature.techinfo.infrastructure.persistence.cache;

import org.bson.types.ObjectId;

import java.util.Map;

public interface ViewCacheRepository {

    void merge(ObjectId articleId, long value);

    Map<ObjectId, Long> retrieveAll();

    void clear();

}
