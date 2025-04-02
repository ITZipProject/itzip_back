package darkoverload.itzip.feature.techinfo.application.event.handler;

import darkoverload.itzip.feature.techinfo.application.event.payload.ArticleHiddenEvent;
import darkoverload.itzip.feature.techinfo.application.service.command.CommentCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
