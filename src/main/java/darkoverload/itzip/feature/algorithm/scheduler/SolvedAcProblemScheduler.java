package darkoverload.itzip.feature.algorithm.scheduler;

import darkoverload.itzip.feature.algorithm.service.AlgorithmService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SolvedAcProblemScheduler {
    private final AlgorithmService algorithmService;

    /**
     * solvedac문제 업데이트
     */
    @Scheduled(cron = "${SOLVED_AC_SCHEDULER_CRON}")
    public void solvedProblemUpdate(){
        log.info("==== solved.ac 작업시작 ====");
        algorithmService.saveProblemTags();
        log.info("==== tag 저장완료 ====");
//        algorithmService.saveProblems();
        log.info("==== problem 저장완료 ====");
    }
// 디버깅용 코드
    @PostConstruct
    public void runOnceOnStartup() {
        solvedProblemUpdate();
    }
}