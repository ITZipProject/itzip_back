package darkoverload.itzip.feature.job.repository;

import darkoverload.itzip.feature.job.domain.JobInfo;
import darkoverload.itzip.feature.job.entity.JobInfoEntity;
import darkoverload.itzip.feature.job.repository.custom.CustomJobInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface JobInfoRepository extends JpaRepository<JobInfoEntity, Long>, CustomJobInfoRepository {

}
