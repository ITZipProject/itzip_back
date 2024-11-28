package darkoverload.itzip.feature.job.repository;

import darkoverload.itzip.feature.job.domain.JobInfo;
import darkoverload.itzip.feature.job.repository.custom.CustomJobInfoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobInfoRepository extends JpaRepository<JobInfo, Long>, CustomJobInfoRepository {

    @Override
    JobInfo getReferenceById(Long id);
}
