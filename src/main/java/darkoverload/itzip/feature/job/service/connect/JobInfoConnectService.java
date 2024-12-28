package darkoverload.itzip.feature.job.service.connect;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.job.JobInfoAggregator;

import java.util.List;

public interface JobInfoConnectService {
    List<JobInfo> jobInfoConnect();

    Long jobInfoDelete(JobInfoAggregator jobInfoAggregator);

    Long jobInfoUpdate(JobInfoAggregator jobInfoAggregator);

    Long jobInfoSave(JobInfoAggregator jobInfoAggregator);

}
