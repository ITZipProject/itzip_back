package darkoverload.itzip.feature.job.repository.custom;

import darkoverload.itzip.feature.job.entity.JobInfoScrapEntity;

import java.util.List;
import java.util.Optional;

public interface CustomJobInfoScrapRepository {
      void bulkDeleteByPositionIds(List<Long> positionIds);

    Optional<JobInfoScrapEntity> findByJobInfoId(Long id,String email);

}
