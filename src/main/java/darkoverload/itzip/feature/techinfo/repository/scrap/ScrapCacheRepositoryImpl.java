package darkoverload.itzip.feature.techinfo.repository.scrap;

import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatus;
import darkoverload.itzip.feature.techinfo.repository.scrap.redis.RedisScrapRepository;
import darkoverload.itzip.feature.techinfo.service.scrap.port.ScrapCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Redis 를 사용하여 스크랩 상태를 캐싱하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class ScrapCacheRepositoryImpl implements ScrapCacheRepository {

    private final RedisScrapRepository repository;

    /**
     * 사용자의 포스트 스크랩 상태를 캐시에 저장합니다.
     *
     * @param userId    사용자 ID
     * @param postId    포스트 ID
     * @param isScraped 스크랩 상태
     * @param ttl       캐시 유효 시간(초)
     */
    @Override
    public void save(Long userId, String postId, boolean isScraped, long ttl) {
        repository.save(userId, postId, isScraped, ttl);
    }

    /**
     * 사용자의 특정 포스트에 대한 스크랩 상태를 캐시에서 조회합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 스크랩 상태 (Boolean), 캐시에 없으면 null
     */
    @Override
    public Boolean getScrapStatus(Long userId, String postId) {
        return repository.getScrapStatus(userId, postId);
    }

    /**
     * 캐시에 저장된 모든 스크랩 상태를 조회합니다.
     *
     * @return List<ScrapStatus>
     */
    @Override
    public List<ScrapStatus> getAllScrapStatuses() {
        return repository.getAllScrapStatuses();
    }

    /**
     * 캐시에 저장된 모든 스크랩 데이터를 삭제합니다.
     * 주로 테스트 환경이나 데이터 초기화에 사용됩니다.
     */
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}
