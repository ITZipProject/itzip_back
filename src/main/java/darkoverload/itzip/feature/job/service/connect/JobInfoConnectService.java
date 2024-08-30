package darkoverload.itzip.feature.job.service.connect;

import darkoverload.itzip.feature.job.domain.JobInfo;

import java.util.List;

public interface JobInfoConnectService {
    List<JobInfo> jobInfoConnect();

    void jobInfoDelete(List<JobInfo> apiDataList, List<JobInfo> dbList);

    void jobInfoUpdate(List<JobInfo> apiDataList, List<JobInfo> dbList);
}
