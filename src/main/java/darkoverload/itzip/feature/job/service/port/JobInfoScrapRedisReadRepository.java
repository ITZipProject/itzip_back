package darkoverload.itzip.feature.job.service.port;

import java.util.Set;

public interface JobInfoScrapRedisReadRepository {

    boolean hasSameJobInfoScrap(Long jobInfoId, String userEmail);

    boolean isJobInfoScrapStatus(Long jobInfoId, String userEmail);

    Set<String> getJobInfoScrapListFromRedis();

    String getJobInfoScrapCount(Long jobInfoId);

    String getJobInfoStatus(Long jobInfoId, String userEmail);

}
