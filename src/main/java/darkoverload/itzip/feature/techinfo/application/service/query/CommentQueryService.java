package darkoverload.itzip.feature.techinfo.application.service.query;

import darkoverload.itzip.feature.techinfo.ui.payload.response.CommentResponse;
import org.springframework.data.domain.Page;

public interface CommentQueryService {

    Page<CommentResponse> getCommentsByArticleId(String articleId, int page, int size);

}
