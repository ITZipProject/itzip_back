package darkoverload.itzip.feature.algorithm.scheduler;

import darkoverload.itzip.feature.algorithm.service.AlgorithmService;
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
        log.info("==== 작업시작 ====");
        algorithmService.saveProblems();
        log.info("==== 작업완료 ====");
    }
}