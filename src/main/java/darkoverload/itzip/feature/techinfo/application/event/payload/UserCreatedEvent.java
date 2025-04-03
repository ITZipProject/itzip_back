package darkoverload.itzip.feature.techinfo.application.event.payload;

/**
 * 사용자 생성 이벤트를 나타내는 레코드입니다.
 *
 * <p>사용자가 생성되었을 때 발생하며, 생성된 사용자의 식별자를 포함합니다.</p>
 */
public record UserCreatedEvent(Long userId) {
}
