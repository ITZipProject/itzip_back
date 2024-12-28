package darkoverload.itzip.feature.job.service.connect.port;

import darkoverload.itzip.feature.job.domain.job.JobInfo;

import java.util.List;

public interface JobInfoConnectRepository {

    int deleteAll(List<Long> positionId);

    int saveAll(List<JobInfo> jobInfos);

    int updateAll(List<JobInfo> jobInfos);

}
