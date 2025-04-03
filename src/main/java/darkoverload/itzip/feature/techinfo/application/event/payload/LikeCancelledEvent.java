package darkoverload.itzip.feature.techinfo.application.event.payload;

/**
 * 좋아요 취소 이벤트를 나타내는 레코드입니다.
 *
 * <p>아티클의 좋아요가 취소될 때 발생하며, 해당 아티클의 식별자를 포함합니다.</p>
 */
public record LikeCancelledEvent(String articleId) {
}
