package darkoverload.itzip.feature.techinfo.service.like;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

/**
 * 좋아요 기능을 제공하는 서비스 인터페이스.
 * 사용자가 특정 게시물에 대해 좋아요 상태를 확인하고 변경하는 기능을 정의.
 */
public interface LikeService {

    /**
     * 사용자의 좋아요 상태를 반전시키는 메서드.
     * 좋아요가 없으면 추가하고, 이미 있으면 취소.
     *
     * @param userDetails 좋아요 상태를 변경할 사용자 정보
     * @param postId 좋아요 상태를 변경할 게시물의 ID
     * @return 좋아요가 추가되었으면 true, 취소되었으면 false
     */
    boolean toggleLike(CustomUserDetails userDetails, String postId);

    /**
     * 사용자가 특정 게시물에 좋아요를 눌렀는지 확인하는 메서드.
     *
     * @param userId 사용자 정보
     * @param postId 게시물 ID
     * @return 좋아요를 눌렀으면 true, 아니면 false
     */
    boolean isLiked(Long userId, String postId);
}