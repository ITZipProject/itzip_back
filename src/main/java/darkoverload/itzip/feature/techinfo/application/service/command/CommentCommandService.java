package darkoverload.itzip.feature.techinfo.application.service.command;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.ui.payload.request.comment.CommentEditRequest;
import darkoverload.itzip.feature.techinfo.ui.payload.request.comment.CommentRegistrationRequest;

public interface CommentCommandService {

    void create(CustomUserDetails userDetails, CommentRegistrationRequest request);

    void update(CustomUserDetails userDetails, CommentEditRequest request);

    void delete(CustomUserDetails userDetails, Long commentId);

    void deleteByArticleId(String articleId);

}
