package darkoverload.itzip.feature.job.service.port;

import java.util.List;
import java.util.Set;

public interface JobInfoScrapRedisRepository {

    void saveScrapInfoToRedis(Long jobInfoId, String userEmail);

    void unScrapInfoFromRedis(Long jobInfoId, String userEmail);

    void notCacheUnScrapInfoToRedis(Long jobInfoId, String userEmail);

    void incrementScrapCountToRedis(Long jobInfoId);

    void decrementScrapCountFromRedis(Long jobInfoId);

    boolean isJobInfoScrapStatus(Long jobInfoId, String userEmail);

    Set<String> getJobInfoScrapListFromRedis();

    boolean hasSameJobInfoScrap(Long jobInfoId, String userEmail);

    void deleteScrapCountToRedis(Long jobInfoId);

    void deleteScrapInfo(Long jobInfoId, String userEmail);

    String getJobInfoScrapCount(Long jobInfoId);

    String getJobInfoStatus(Long jobInfoId, String userEmail);

}
