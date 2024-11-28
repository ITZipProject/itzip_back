package darkoverload.itzip.feature.techinfo.service.scrap.port;

import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatus;

import java.util.List;

public interface ScrapCacheRepository {

    void save(Long userId, String postId, boolean isScraped, long ttl);

    Boolean getScrapStatus(Long userId, String postId);

    List<ScrapStatus> getAllScrapStatuses();

}
