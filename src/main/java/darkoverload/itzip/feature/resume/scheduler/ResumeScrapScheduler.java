package darkoverload.itzip.feature.resume.scheduler;

import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrap;
import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrapType;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import darkoverload.itzip.feature.resume.service.resume.ResumeCommandService;
import darkoverload.itzip.feature.resume.service.resume.ResumeReadService;
import darkoverload.itzip.feature.resume.service.resume.redis.ResumeScrapRedisService;
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
public class ResumeScrapScheduler {
    private final static int delayNumber = 10000;

    private final ResumeCommandService resumeCommandService;
    private final ResumeReadService resumeReadService;
    private final ResumeScrapRedisService resumeScrapRedisService;
    private final UserService userService;

    @Transactional
    @Scheduled(fixedDelay = delayNumber)
    public void synchronizeResumeScraps() {
        Set<String> redisKeys = resumeScrapRedisService.getScrapKeysFromRedis();

        if(redisKeys.isEmpty()) {
            return ;
        }

        for(String redisKey : redisKeys) {
            String[] keyParts = JobInfoScrap.getRedisKeyParts(redisKey);
            String userEmail = keyParts[2];
            Long resumeId = Long.valueOf(keyParts[1]);
            syncResumeScrapToDatabase(userEmail, resumeId);
        }

    }

    private void syncResumeScrapToDatabase(String userEmail, Long resumeId) {
        User user = userService.getByEmail(userEmail);
        ResumeEntity resume = resumeReadService.getById(resumeId);

        ResumeScrap resumeScrap = ResumeScrap.createScrap(user.convertToEntity(), resume);
        ResumeScrap databaseScrap = resumeReadService.findByResumeScrap(resumeId, userEmail);

        if(ResumeScrapType.isUnScrapEqual(resumeScrapRedisService.getResumeStatusFromRedis(resumeId, userEmail)) && databaseScrap != null) {
            resumeCommandService.scrapDelete(databaseScrap);
            updateScrapCount(resumeId, resume.convertToDomain());
            resumeScrapRedisService.resumeScrapDeleteToRedis(resumeId, userEmail);
            log.info("=== resumeScrap delete ===");
            return ;
        }

        log.info("=== resume save ===");
        resumeCommandService.resumeScrapSave(resumeScrap);
        updateScrapCount(resumeId, resume.convertToDomain());
        resumeScrapRedisService.resumeScrapDeleteToRedis(resumeId, userEmail);
    }

    private void updateScrapCount(Long jobInfoId, Resume resume) {
        int scrapCountFromRedis = Integer.parseInt(resumeScrapRedisService.getJobInfoScrapCountFromRedis(jobInfoId));
        int scrapCount = resume.updateScrapCount(scrapCountFromRedis);
        log.info("==== scrapCount :: {} ====", scrapCount);
        resumeCommandService.updateScrapCount(resume);
    }

}
