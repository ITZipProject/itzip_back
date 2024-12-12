package darkoverload.itzip.feature.techinfo.repository.scrap.redis;

import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatus;

import java.util.List;

public interface RedisScrapRepository {

    void save(Long userId, String postId, boolean isScraped, long ttl);

    Boolean getScrapStatus(Long userId, String postId);

    List<ScrapStatus> getAllScrapStatuses();

    void deleteAll();

}
