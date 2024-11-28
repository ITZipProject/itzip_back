package darkoverload.itzip.feature.job.scheduler;

import darkoverload.itzip.feature.job.domain.JobInfo;
import darkoverload.itzip.feature.job.repository.JobInfoRepository;
import darkoverload.itzip.feature.job.service.connect.JobInfoConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobInfoScheduler {
    private static final String JOB_INFO_SCHEDULER_CON = "1 30 0 * * *";

    private final JobInfoRepository jobInfoRepository;
    private final JobInfoConnectService service;
    /**
     * Saramin API에서 최신 JobInfo 데이터를 가져와 데이터베이스와 비교하여
     * 삭제, 업데이트, 삽입 작업을 수행하는 메서드입니다. 이 메서드는 매일 00:30에 실행됩니다.
     *
     * @Transactional 트랜잭션이 보장되는 환경에서 실행되며, 작업이 완료될 때까지
     * 트랜잭션이 유지됩니다. 오류가 발생하면 모든 작업이 롤백됩니다.
     *
     * @Scheduled(cron = "1 30 0 * * *") 크론 표현식을 사용하여 매일 01:30에 실행됩니다.
     */

    @Transactional
    @Scheduled(cron = JOB_INFO_SCHEDULER_CON)
    public void jobInfoConnectApi() {
        // 데이터베이스에서 모든 JobInfo 데이터를 조회하고, 도메인 객체 리스트로 변환
        List<JobInfo> dbList = jobInfoRepository.findAll().stream().toList();

        // Saramin API를 호출하여 최신 JobInfo 데이터를 가져옴
        List<JobInfo> apiDataList = service.jobInfoConnect();

        // 데이터베이스에 있는 JobInfo 데이터를 API 데이터와 비교하여 삭제 작업 수행
        Long deletedCount = service.jobInfoDelete(apiDataList, dbList);

        log.info("==== Saramin API Data deleteCount :: {} ====", deletedCount);

        // 데이터베이스에 있는 JobInfo 데이터를 API 데이터와 비교하여 업데이트 작업 수행
        Long updatedCount = service.jobInfoUpdate(apiDataList, dbList);

        log.info("==== Saramin API Data updateCount :: {} ====", updatedCount);

        // API 데이터 중에서 데이터베이스에 없는 데이터를 삽입하는 작업 수행
        Long savedCount = service.jobInfoSave(apiDataList, dbList);

        log.info("==== Saramin API Data updateCount :: {} ====", savedCount);
    }

}
