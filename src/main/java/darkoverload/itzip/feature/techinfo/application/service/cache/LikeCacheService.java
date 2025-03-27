package darkoverload.itzip.feature.techinfo.application.service.cache;

public interface LikeCacheService {

    void merge(String articleId);

    void subtract(String articleId);

    void flush();

}
