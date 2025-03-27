package darkoverload.itzip.feature.techinfo.application.service.cache;

import org.bson.types.ObjectId;

public interface ViewCacheService {

    void merge(ObjectId articleId);

    void flush();

}
