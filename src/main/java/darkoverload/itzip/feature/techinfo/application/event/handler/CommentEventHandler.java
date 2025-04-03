package darkoverload.itzip.feature.techinfo.application.event.handler;

import darkoverload.itzip.feature.techinfo.application.event.payload.ArticleHiddenEvent;
import darkoverload.itzip.feature.techinfo.application.service.command.CommentCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 아티클 삭제(숨김) 이벤트를 처리하여 해당 아티클의 댓글을 삭제(숨김) 수행하는 핸들러입니다.
 */
@Component
public class CommentEventHandler {

    private final CommentCommandService commandService;

    public CommentEventHandler(final CommentCommandService commandService) {
        this.commandService = commandService;
    }

    @EventListener
    public void handleArticleHidden(final ArticleHiddenEvent event) {
        final String articleIdHex = event.articleId().toHexString();
        commandService.deleteByArticleId(articleIdHex);
    }

}
