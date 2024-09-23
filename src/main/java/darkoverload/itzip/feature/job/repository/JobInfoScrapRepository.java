package darkoverload.itzip.feature.job.repository;

import darkoverload.itzip.feature.job.entity.JobInfoScrapEntity;
import darkoverload.itzip.feature.job.repository.custom.CustomJobInfoScrapRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobInfoScrapRepository extends JpaRepository<JobInfoScrapEntity, Long> , CustomJobInfoScrapRepository {

}
