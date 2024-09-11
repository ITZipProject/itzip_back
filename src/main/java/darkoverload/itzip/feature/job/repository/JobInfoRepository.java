package darkoverload.itzip.feature.job.repository;

import darkoverload.itzip.feature.job.entity.JobInfoEntity;
import darkoverload.itzip.feature.job.repository.custom.CustomJobInfoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobInfoRepository extends JpaRepository<JobInfoEntity, Long>, CustomJobInfoRepository {

    @Override
    JobInfoEntity getReferenceById(Long id);
}
