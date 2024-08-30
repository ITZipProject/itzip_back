package darkoverload.itzip.feature.job.scheduler;

import darkoverload.itzip.feature.job.domain.JobInfo;
import darkoverload.itzip.feature.job.entity.JobInfoEntity;
import darkoverload.itzip.feature.job.repository.JobInfoRepository;
import darkoverload.itzip.feature.job.service.connect.JobInfoConnectService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobInfoScheduler {

    private final JobInfoRepository jobInfoRepository;
    private final JobInfoConnectService service;


    @Transactional
    @Scheduled(cron = "0 30 0 * * *")
    public void jobInfoConnectApi() {

        List<JobInfo> dbList = jobInfoRepository.findAll().stream().map(JobInfoEntity::toDomain).toList();

        List<JobInfo> apiDataList = service.jobInfoConnect();

        Long deletedCount = service.jobInfoDelete(apiDataList, dbList);

        log.debug("==== Saramin API Data deleteCount :: {} ====", deletedCount);

        Long updatedCount = service.jobInfoUpdate(apiDataList, dbList);

        log.debug("==== Saramin API Data updateCount :: {} ====", updatedCount);

        Long savedCount = service.jobInfoSave(apiDataList, dbList);

        log.debug("==== Saramin API Data updateCount :: {} ====", savedCount);

    }
}
