package darkoverload.itzip.feature.job.repository.connect;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.service.connect.port.JobInfoConnectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JobInfoConnectRepositoryImpl implements JobInfoConnectRepository{

    private final JobInfoConnectJdbcRepository repository;

    @Override
    public int deleteAll(List<Long> positionId) {
        return repository.deleteAll(positionId);
    }

    public int saveAll(List<JobInfo> jobInfos) {
        return repository.saveAll(jobInfos);
    }

    public int updateAll(List<JobInfo> jobInfos) {
        return repository.updateAll(jobInfos);
    }

}
