package darkoverload.itzip.feature.job.service.connect;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.job.JobInfoAggregator;

import java.util.List;

public interface JobInfoConnectService {
    List<JobInfo> jobInfoConnect();

    int jobInfoDelete(JobInfoAggregator jobInfoAggregator);

    int jobInfoUpdate(JobInfoAggregator jobInfoAggregator);

    int jobInfoSave(JobInfoAggregator jobInfoAggregator);

}
