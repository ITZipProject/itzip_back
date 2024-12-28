package darkoverload.itzip.feature.job.repository;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.repository.custom.jobinfo.CustomJobInfoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobInfoJpaRepository extends JpaRepository<JobInfo, Long>, CustomJobInfoRepository {

    JobInfo getReferenceById(Long id);

    JobInfo findByPositionId(Long positionId);

}
