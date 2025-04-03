package darkoverload.itzip.feature.techinfo.application.event.handler;

import darkoverload.itzip.feature.techinfo.application.event.payload.UserCreatedEvent;
import darkoverload.itzip.feature.techinfo.application.service.command.BlogCommandService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 회원 생성 이벤트를 처리하여 블로그 생성을 수행하는 이벤트 핸들러입니다.
 *
 * <p>
 *     트랜잭션이 커밋된 후에 실행되며,
 *     {@link UserCreatedEvent} 발생 시 {@link BlogCommandService#create(Long)} 를 호출합니다.
 * </p>
 */
@Component
public class BlogEventHandler {

    private final BlogCommandService blogCommandService;

    public BlogEventHandler(final BlogCommandService blogCommandService) {
        this.blogCommandService = blogCommandService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserCreated(final UserCreatedEvent event) {
        blogCommandService.create(event.userId());
    }

}
