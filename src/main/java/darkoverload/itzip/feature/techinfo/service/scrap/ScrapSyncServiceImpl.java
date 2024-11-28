package darkoverload.itzip.feature.techinfo.service.scrap;

import darkoverload.itzip.feature.techinfo.domain.scrap.Scrap;
import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatus;
import darkoverload.itzip.feature.techinfo.service.scrap.port.ScrapCacheRepository;
import darkoverload.itzip.feature.techinfo.service.scrap.port.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 스크랩 동기화 서비스 구현 클래스.
 * 캐시된 스크랩 정보를 MongoDB에 주기적으로 동기화합니다.
 */
@Service
@RequiredArgsConstructor
public class ScrapSyncServiceImpl implements ScrapSyncService {

    private final ScrapCacheRepository scrapCacheRepository;
    private final ScrapRepository scrapRepository;

    /**
     * 캐시된 스크랩 정보를 MongoDB에 동기화합니다.
     * 이 메소드는 설정된 스케줄에 따라 주기적으로 실행됩니다.
     */
    @Override
    @Scheduled(cron = "${TECHINFO_SCRAP_SCHEDULER_CRON}")
    public void persistScrapsToMongo() {
        List<ScrapStatus> cachedScraps = scrapCacheRepository.getAllScrapStatuses();

        for (ScrapStatus scrapStatus : cachedScraps) {
            ObjectId postId = new ObjectId(scrapStatus.getPostId());
            Long userId = scrapStatus.getUserId();
            boolean isScrapped = scrapStatus.getIsScrapped();
            boolean exists = scrapRepository.existsByUserIdAndPostId(userId, postId);

            if (isScrapped && !exists) {
                Scrap scrap = Scrap.from(scrapStatus);
                scrapRepository.save(scrap);
            } else if (!isScrapped && exists) {
                scrapRepository.deleteByUserIdAndPostId(userId, postId);
            }
        }
    }

}
