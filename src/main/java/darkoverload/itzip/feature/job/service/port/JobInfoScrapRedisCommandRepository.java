package darkoverload.itzip.feature.job.service.port;

import java.util.Set;

public interface JobInfoScrapRedisCommandRepository {

    void saveScrapInfoToRedis(Long jobInfoId, String userEmail);

    void unScrapInfoFromRedis(Long jobInfoId, String userEmail);

    void notCacheUnScrapInfoToRedis(Long jobInfoId, String userEmail);

    void incrementScrapCountToRedis(Long jobInfoId);

    void decrementScrapCountFromRedis(Long jobInfoId);

    void deleteScrapCountToRedis(Long jobInfoId);

    void deleteScrapInfo(Long jobInfoId, String userEmail);

}
