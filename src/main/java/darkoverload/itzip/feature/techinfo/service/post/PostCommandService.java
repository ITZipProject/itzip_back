package darkoverload.itzip.feature.techinfo.service.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostUpdateRequest;

public interface PostCommandService {

    void create(CustomUserDetails userDetails, PostCreateRequest request);

    void update(PostUpdateRequest request);

    void update(String postId, boolean status);

    void updateFieldWithValue(String postId, String fieldName, int value);

}
