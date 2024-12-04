package darkoverload.itzip.feature.techinfo.service.blog;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.request.BlogUpdateIntroRequest;
import darkoverload.itzip.feature.user.domain.User;

public interface BlogCommandService {

    void create(User user);

    void update(CustomUserDetails userDetails, BlogUpdateIntroRequest request);

    void updateStatus(Long blogId, boolean status);

}
