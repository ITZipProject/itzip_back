package darkoverload.itzip.feature.techinfo.application.service.query;

import darkoverload.itzip.feature.techinfo.ui.payload.response.BlogResponse;
import darkoverload.itzip.feature.techinfo.domain.entity.Blog;

import java.util.Map;
import java.util.Set;

public interface BlogQueryService {

    BlogResponse getBlogResponseById(Long id);

    Blog getBlogById(Long id);

    BlogResponse getBlogResponseByUserNickname(String nickname);

    Long getBlogIdByUserNickname(String nickname);

    Map<Long, Blog> getBlogMapByIds(Set<Long> blogIds);

}
