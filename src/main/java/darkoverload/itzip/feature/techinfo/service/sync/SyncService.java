package darkoverload.itzip.feature.techinfo.service.sync;

package darkoverload.itzip.feature.techinfo.service.sync;

/**
 * 좋아요 상태를 Redis 캐시에서 MongoDB로 동기화하는 로직을 처리하는 서비스 인터페이스.
 * 주기적으로 Redis 캐시에 저장된 좋아요 상태를 조회하고 이를 MongoDB에 반영하는 메서드를 정의한다.
 */
public interface SyncService {

    /**
     * 주기적으로 호출되어 Redis 캐시에서 저장된 좋아요 상태를 MongoDB에 반영.
     */
    void persistLikesToMongo();
}