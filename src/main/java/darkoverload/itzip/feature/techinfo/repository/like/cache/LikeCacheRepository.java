package darkoverload.itzip.feature.techinfo.repository.like.cache;

import darkoverload.itzip.feature.techinfo.dto.like.LikeStatusDto;

import java.util.List;

/**
 * 좋아요 상태를 캐시에 저장하고 조회하는 작업을 처리하는 리포지토리 인터페이스.
 * 유저의 특정 포스트에 대한 좋아요 상태를 캐시에서 관리함.
 */
public interface LikeCacheRepository {

    /**
     * 특정 유저가 특정 포스트에 대해 좋아요를 눌렀는지 여부를 캐시에 저장.
     *
     * @param userId 유저 ID
     * @param postId 포스트 ID
     * @param isLiked 좋아요 여부
     * @param ttl 캐시 유효 기간 (초 단위)
     */
    void setLikeStatus(Long userId, String postId, boolean isLiked, long ttl);

    /**
     * 특정 유저가 특정 포스트에 대해 좋아요를 눌렀는지 여부를 캐시에서 조회.
     *
     * @param userId 유저 ID
     * @param postId 포스트 ID
     * @return 좋아요 상태가 캐시에 존재하면 true/false, 존재하지 않으면 null
     */
    Boolean getLikeStatus(Long userId, String postId);

    /**
     * 모든 유저와 포스트에 대한 좋아요 상태를 캐시에서 조회.
     *
     * @return 좋아요 상태 리스트
     */
    List<LikeStatusDto> getAllLikeStatuses();
}