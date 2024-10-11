package darkoverload.itzip.feature.techinfo.service.scrap.impl;

import darkoverload.itzip.feature.techinfo.domain.Scrap;
import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatusDto;
import darkoverload.itzip.feature.techinfo.repository.scrap.ScrapRepository;
import darkoverload.itzip.feature.techinfo.repository.scrap.cache.ScrapCacheRepository;
import darkoverload.itzip.feature.techinfo.service.scrap.ScrapSyncService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapSyncServiceImpl implements ScrapSyncService {

    private final ScrapRepository scrapRepository;
    private final ScrapCacheRepository scrapCacheRepository;

    @Scheduled(cron = "${TECHINFO_SCRAP_SCHEDULER_CRON}")
    @Override
    public void persistScrapsToMongo() {
        List<ScrapStatusDto> cachedScraps = scrapCacheRepository.getAllScrapStatuses(); // 캐시에서 모든 스크랩 상태 조회

        for (ScrapStatusDto scrapStatus : cachedScraps) {
            ObjectId postId = new ObjectId(scrapStatus.getPostId());
            Long userId = scrapStatus.getUserId();
            boolean isScrapped = scrapStatus.getIsScrapped();
            boolean exists = scrapRepository.existsByUserIdAndPostId(userId, postId);

            if (isScrapped && !exists) {
                // 스크랩 추가
                Scrap scrap = scrapStatus.convertToDomain(); // DTO를 도메인 객체로 변환
                scrapRepository.save(scrap.convertToDocumentWithoutScrapId()); // 스크랩 추가
            } else if (!isScrapped && exists) {
                // 스크랩 취소
                scrapRepository.deleteByUserIdAndPostId(userId, postId); // 스크랩 취소
            }
        }
    }
}
