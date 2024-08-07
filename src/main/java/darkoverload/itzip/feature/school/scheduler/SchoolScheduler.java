package darkoverload.itzip.feature.school.scheduler;

import darkoverload.itzip.feature.school.service.connect.SchoolConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchoolScheduler {

    private SchoolConnectService connectService;


    /**
     * 학교 정보 배치 스케줄러
     * 1,4,7,10월 1일날 배치 작업
     */
    @Scheduled(cron = "0 0 0 1 1,4,7,10 ?")
    public void schoolInfoInsert(){
        log.info("==== 작업완료 ====");

        // 테이블에 총 개수 가져온다.
        Long count = connectService.getTotalCount();

        // 총 개수가 0보다 크면 테이블 정보를 모두 deleteAll
        if(count > 0) connectService.deleteAll();

        // 학교 정보 api 데이터 실질적으로 저장
        connectService.connectSchoolApi();

        log.info("==== 작업 종료 ====");
    }
}
