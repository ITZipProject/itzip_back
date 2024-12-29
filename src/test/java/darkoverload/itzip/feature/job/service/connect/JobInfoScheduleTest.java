//package darkoverload.itzip.feature.job.service.connect;
//
//import darkoverload.itzip.feature.job.domain.job.JobInfoAggregator;
//import darkoverload.itzip.feature.job.domain.job.JobInfos;
//import darkoverload.itzip.feature.job.repository.JobInfoJpaRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@Transactional
//@SpringBootTest
//@ActiveProfiles(profiles = "test")
//public class JobInfoScheduleTest {
//
//    @Autowired
//    private JobInfoJpaRepository jobInfoJpaRepository;
//
//    @Autowired
//    private JobInfoConnectService service;
//
//    @Test
//    void 사람인_테스트() {
//        // 데이터베이스에서 모든 JobInfo 데이터를 조회하고, 도메인 객체 리스트로 변환
//        JobInfos dbJobInfos = new JobInfos(jobInfoJpaRepository.findAll().stream().toList());
//
//        // Saramin API를 호출하여 최신 JobInfo 데이터를 가져옴
//        JobInfos apiJobInfos = new JobInfos(service.jobInfoConnect());
//
//        JobInfoAggregator aggregator = JobInfoAggregator.create(apiJobInfos, dbJobInfos);
//
//        // 데이터베이스에 있는 JobInfo 데이터를 API 데이터와 비교하여 삭제 작업 수행
//        int deleteCount = service.jobInfoDelete(aggregator);
//        log.info("==== Saramin API Data deleteCount :: {} ====", deleteCount);
//
//        // 데이터베이스에 있는 JobInfo 데이터를 API 데이터와 비교하여 업데이트 작업 수행
//        int updateCount = service.jobInfoUpdate(aggregator);
//        log.info("==== Saramin API Data updateCount :: {} ====", updateCount);
//
//        // API 데이터 중에서 데이터베이스에 없는 데이터를 삽입하는 작업 수행
//        int saveCount = service.jobInfoSave(aggregator);
//        log.info("==== Saramin API Data Data saveCount :: {} ====", saveCount);
//    }
//
//}
