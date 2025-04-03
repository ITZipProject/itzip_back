package darkoverload.itzip.feature.techinfo.application.event.payload;

/**
 * 좋아요 이벤트를 나타내는 레코드입니다.
 *
 * <p>아티클에 좋아요가 발생했을 때 발생하며, 해당 게시글의 식별자를 포함합니다.</p>
 */
public record LikedEvent(String articleId) {
}
