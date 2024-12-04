package darkoverload.itzip.feature.job.scheduler;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrapType;
import darkoverload.itzip.feature.job.service.JobInfoScrapRedisService;
import darkoverload.itzip.feature.job.service.JobInfoService;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class JobInfoScrapScheduler {

    private final static int delayNumber = 10000;

    private final JobInfoService jobInfoService;

    private final JobInfoScrapRedisService jobInfoScrapRedisService;

    private final UserService userService;

    @Transactional
    @Scheduled(fixedDelay = delayNumber)
    public void synchronizeJobInfoScraps() {
        Set<String> redisKeys = jobInfoScrapRedisService.getScrapKeysFromRedis();

        if (redisKeys.isEmpty()) {
            return;
        }

        for (String redisKey : redisKeys) {
            String[] keyParts = JobInfoScrap.getRedisKeyParts(redisKey);
            String userEmail = keyParts[2];
            Long jobInfoId = Long.valueOf(keyParts[1]);
            syncJobInfoScrapToDatabase(userEmail, jobInfoId);
        }
    }

    private void syncJobInfoScrapToDatabase(String userEmail, Long jobInfoId) {
        User user = userService.getByEmail(userEmail);
        JobInfo jobInfo = jobInfoService.getById(jobInfoId);

        JobInfoScrap jobInfoScrap = JobInfoScrap.createScrap(user.convertToEntity(), jobInfo);
        JobInfoScrap scrapDatabase = jobInfoService.findByJobInfoId(jobInfoId, userEmail);

        if (JobInfoScrapType.isUnScrapEqual(jobInfoScrapRedisService.getJobInfoStatusFromRedis(jobInfoId, userEmail)) && scrapDatabase != null) {
            jobInfoService.delete(scrapDatabase);
            updateScrapCount(jobInfoId, jobInfo);
            jobInfoScrapRedisService.jobInfoScrapDeleteToRedis(jobInfoId, userEmail);
            log.info("=== jobInfoScrap delete ===");
            return;
        }

        log.info("=== jobInfo save ===");
        jobInfoService.jobInfoScrapSave(jobInfoScrap);
        updateScrapCount(jobInfoId, jobInfo);
        jobInfoScrapRedisService.jobInfoScrapDeleteToRedis(jobInfoId, userEmail);

    }

    private void updateScrapCount(Long jobInfoId, JobInfo jobInfo) {
        int scrapCountFromRedis = Integer.parseInt(jobInfoScrapRedisService.getJobInfoScrapCountFromRedis(jobInfoId));
        int scrapCount = jobInfo.updateScrapCount(scrapCountFromRedis);
        log.info("==== scrapCount :: {} ====", scrapCount);
        jobInfoService.updateScrapCount(jobInfo);
    }

}
