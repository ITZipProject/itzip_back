package darkoverload.itzip.school.scheduler;

import darkoverload.itzip.school.service.SchoolConnectService;
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
     * 1,4,7,10월 1일날 배치 작업
     */
    @Scheduled(cron = "0 0 0 1 1,4,7,10 ?")
    public void schoolInfoInsert(){
        log.info("==== 작업완료 ====");

        Long count = connectService.getTotalCount();

//      0보다 큰 경우만 deleteAll
        if(count > 0) connectService.deleteAll();

        // api 정보 실질적으로 저장하는 부분
        connectService.connectSchoolApi();
        log.info("==== 작업 종료 ====");
    }
}
