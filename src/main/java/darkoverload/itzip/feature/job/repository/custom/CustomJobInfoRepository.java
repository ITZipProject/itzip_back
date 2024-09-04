package darkoverload.itzip.feature.job.repository.custom;

import java.util.List;

public interface CustomJobInfoRepository {
    long bulkDeleteByPositionIds(List<Long> positionIds);
}
