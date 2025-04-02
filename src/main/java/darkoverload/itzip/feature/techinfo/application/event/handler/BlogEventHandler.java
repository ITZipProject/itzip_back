package darkoverload.itzip.feature.techinfo.application.event.handler;

import darkoverload.itzip.feature.techinfo.application.event.payload.UserCreatedEvent;
import darkoverload.itzip.feature.techinfo.application.service.command.BlogCommandService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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
