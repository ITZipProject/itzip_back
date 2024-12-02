package darkoverload.itzip.feature.job.service.connect;

import darkoverload.itzip.feature.job.domain.job.JobInfo;

import java.util.List;

public interface JobInfoConnectService {
    List<JobInfo> jobInfoConnect();

    Long jobInfoDelete(List<JobInfo> apiDataList, List<JobInfo> dbList);

    Long jobInfoUpdate(List<JobInfo> apiDataList, List<JobInfo> dbList);

    Long jobInfoSave(List<JobInfo> apiDataList, List<JobInfo> dbList);
}
