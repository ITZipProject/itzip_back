package darkoverload.itzip.feature.techinfo.application.service.command;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

public interface ScrapCommandService {

    void create(CustomUserDetails userDetails, String articleId);

    void delete(CustomUserDetails userDetails, String articleId);

}
