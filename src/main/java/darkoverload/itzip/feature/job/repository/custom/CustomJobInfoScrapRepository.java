package darkoverload.itzip.feature.job.repository.custom;

import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import java.util.List;
import java.util.Optional;

public interface CustomJobInfoScrapRepository {
      void bulkDeleteByPositionIds(List<Long> positionIds);

    Optional<JobInfoScrap> findByJobInfoId(Long id, String email);

}
