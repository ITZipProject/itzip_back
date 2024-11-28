package darkoverload.itzip.feature.techinfo.service.comment;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCommentCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCommentUpdateRequest;

public interface CommentCommandService {

    void create(CustomUserDetails userDetails, PostCommentCreateRequest request);

    void update(CustomUserDetails userDetails, PostCommentUpdateRequest request);

    void updateVisibility(CustomUserDetails userDetails, String commentId, boolean status);

}
