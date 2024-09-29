package darkoverload.itzip.feature.techinfo.service.like;

/**
 * 좋아요 기능을 제공하는 서비스 인터페이스.
 * 특정 사용자가 특정 게시물에 대해 좋아요 상태인지 확인하는 기능을 정의함
 */
public interface LikeService {

    /**
     * 사용자의 좋아요 상태를 토글하는 메서드.
     * 현재 사용자가 게시물에 대해 좋아요를 눌렀는지 확인한 후, 상태를 반전시킴.
     * 좋아요가 없으면 추가하고, 이미 있으면 취소함.
     *
     * @param userId 좋아요를 토글할 사용자의 ID
     * @param postId 좋아요 상태를 변경할 게시물의 ID
     * @return 좋아요가 추가되었으면 true, 취소되었으면 false를 반환한다.
     */
    boolean toggleLike(Long userId, String postId);

    /**
     * 사용자가 특정 게시물에 좋아요를 눌렀는지 여부를 확인한다.
     *
     * @param userId 좋아요를 확인할 사용자의 ID
     * @param postId 좋아요 상태를 확인할 게시물의 ID
     * @return 좋아요를 눌렀으면 true, 아니면 false를 반환한다.
     */
    boolean isLiked(Long userId, String postId);
}