package darkoverload.itzip.feature.job.service.connect;

import darkoverload.itzip.feature.job.domain.ConnectJobInfo;
import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.job.JobInfoAggregator;
import darkoverload.itzip.feature.job.domain.job.JobInfoIds;
import darkoverload.itzip.feature.job.domain.job.JobInfos;
import darkoverload.itzip.feature.job.repository.JobInfoScrapRepository;
import darkoverload.itzip.feature.job.service.connect.port.JobInfoConnectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JobInfoConnectServiceImpl implements JobInfoConnectService {

    private final JobInfoScrapRepository jobInfoScrapRepository;
    private final JobInfoConnectRepository jobInfoConnectRepository;

    @Value("${job.api-url}")
    private String jobUrl;

    @Value("${job.api-key}")
    private String jobKey;

    /**
     * API와 연결하여 모든 JobInfo 데이터를 가져옵니다.
     * 데이터를 페이지 단위로 나누어 가져와서 리스트에 추가한 후, 전체 데이터를 반환합니다.
     *
     * @return API로부터 가져온 모든 JobInfo 데이터의 리스트
     */
    @Override
    public List<JobInfo> jobInfoConnect() {
        int totalCount = ConnectJobInfo.getTotalCount(jobUrl, jobKey);

        int pages = calculatePageCount(totalCount);

        List<JobInfo> apiDataList = new ArrayList<>();

        for (int i = 0; i < pages; i++) {
            apiDataList.addAll(ConnectJobInfo.getJobInfoData(jobUrl, jobKey, i));
        }

        // 모든 페이지의 데이터를 가져온 후 반환
        return apiDataList;
    }

    @Override
    public int jobInfoDelete(JobInfoAggregator jobInfoAggregator) {
        if (jobInfoAggregator.isDbJobInfosEmpty()) return 0;
        JobInfoIds jobInfoIds = jobInfoAggregator.makeDeleteJobInfoIds();

        int totalDeletedCount = 0;
        for (int i = 0; i < jobInfoIds.size(); i += 500) {
            jobInfoScrapRepository.bulkDeleteByPositionIds(jobInfoIds.subList(i, Math.min(i + 500, jobInfoIds.size())));
            totalDeletedCount += jobInfoConnectRepository.deleteAll(jobInfoIds.subList(i, Math.min(i + 500, jobInfoIds.size())));
        }

        return totalDeletedCount;
    }

    @Override
    public int jobInfoUpdate(JobInfoAggregator jobInfoAggregator) {
        JobInfos jobInfos = jobInfoAggregator.makeUpdateJobInfos();

        int totalUpdateCount = 0;
        for (int i = 0; i < jobInfos.size(); i += 500) {
            totalUpdateCount+= jobInfoConnectRepository.updateAll(jobInfos.subList(i, Math.min(i + 500, jobInfos.size())));
        }

        return totalUpdateCount;
    }

    @Override
    public int jobInfoSave(JobInfoAggregator jobInfoAggregator) {
        JobInfos saveJobInfos = jobInfoAggregator.makeSaveJobInfos();

        int totalSaveCount = 0;
        for (int i = 0; i < saveJobInfos.size(); i += 500) {
             totalSaveCount += jobInfoConnectRepository.saveAll(saveJobInfos.subList(i, Math.min(i + 500, saveJobInfos.size())));
        }

        return totalSaveCount;
    }

    /**
     * 주어진 총 아이템 수(totalCount)를 기준으로 페이지 수를 계산합니다.
     * 한 페이지에 110개의 아이템이 포함된다고 가정합니다.
     *
     * @param totalCount 전체 아이템 수
     * @return 필요한 총 페이지 수
     */
    private int calculatePageCount(int totalCount) {
        return totalCount / 110 + 1;
    }

}
