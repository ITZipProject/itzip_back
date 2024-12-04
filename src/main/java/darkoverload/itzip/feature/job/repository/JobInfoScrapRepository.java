package darkoverload.itzip.feature.job.repository;

import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import darkoverload.itzip.feature.job.repository.custom.scrap.CustomJobInfoScrapRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobInfoScrapRepository extends JpaRepository<JobInfoScrap, Long> , CustomJobInfoScrapRepository {

}
