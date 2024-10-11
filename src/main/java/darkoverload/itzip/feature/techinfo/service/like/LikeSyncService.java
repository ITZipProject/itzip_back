package darkoverload.itzip.feature.techinfo.service.like;

/**
 * 좋아요 상태를 Redis 캐시에서 MongoDB로 동기화하는 로직을 처리하는 서비스 인터페이스.
 */
public interface LikeSyncService {

    /**
     * 주기적으로 호출되어 Redis 캐시에서 저장된 좋아요 상태를 MongoDB에 반영.
     */
    void persistLikesToMongo();
}