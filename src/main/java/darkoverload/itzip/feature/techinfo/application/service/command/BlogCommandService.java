package darkoverload.itzip.feature.techinfo.application.service.command;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.ui.payload.request.blog.BlogIntroEditRequest;

public interface BlogCommandService {

    void create(Long userId);

    void updateIntro(CustomUserDetails userDetails, BlogIntroEditRequest request);

}
