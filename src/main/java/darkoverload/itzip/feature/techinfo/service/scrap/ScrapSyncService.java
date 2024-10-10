package darkoverload.itzip.feature.techinfo.service.scrap;

/**
 * 스크랩 상태를 Redis 캐시에서 MongoDB로 동기화하는 로직을 처리하는 서비스 인터페이스.
 */
public interface ScrapSyncService {
    /**
     * 주기적으로 호출되어 Redis 캐시에서 저장된 스크랩 상태를 MongoDB에 반영.
     */
    void persistScrapsToMongo();
}
