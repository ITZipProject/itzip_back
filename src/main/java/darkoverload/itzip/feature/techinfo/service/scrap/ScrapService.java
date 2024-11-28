package darkoverload.itzip.feature.techinfo.service.scrap;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

public interface ScrapService {

    boolean toggleScrap(CustomUserDetails userDetails, String postId);

    boolean isScrapped(Long userId, String postId);

}
