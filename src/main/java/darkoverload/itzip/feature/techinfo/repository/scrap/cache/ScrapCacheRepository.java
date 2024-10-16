package darkoverload.itzip.feature.techinfo.repository.scrap.cache;

import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatusDto;

import java.util.List;

public interface ScrapCacheRepository {

    /**
     * 특정 유저가 특정 포스트에 대해 스크랩을 눌렀는지 여부를 캐시에 저장.
     *
     * @param userId 유저 ID
     * @param postId 포스트 ID
     * @param isScraped 스크랩 여부
     * @param ttl 캐시 유효 기간 (초 단위)
     */
    void setScrapStatus(Long userId, String postId, boolean isScraped, long ttl);

    /**
     * 특정 유저가 특정 포스트에 대해 스크랩를 눌렀는지 여부를 캐시에서 조회.
     *
     * @param userId 유저 ID
     * @param postId 포스트 ID
     * @return 스크랩 상태가 캐시에 존재하면 true/false, 존재하지 않으면 null
     */
    Boolean getScrapStatus(Long userId, String postId);

    /**
     * 모든 유저와 포스트에 대한 스크랩 상태를 캐시에서 조회.
     *
     * @return 스크랩 상태 리스트
     */
    List<ScrapStatusDto> getAllScrapStatuses();
}