package darkoverload.itzip.feature.techinfo.application.service.command.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.service.command.ScrapCommandService;
import darkoverload.itzip.feature.techinfo.application.service.query.ArticleQueryService;
import darkoverload.itzip.feature.techinfo.domain.entity.Scrap;
import darkoverload.itzip.feature.techinfo.domain.repository.ScrapRepository;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapCommandServiceImpl implements ScrapCommandService {

    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;

    private final ArticleQueryService articleQueryService;

    @Override
    public void create(final CustomUserDetails userDetails, final String articleId) {
        this.checkUserDetails(userDetails);
        final UserEntity user = userRepository.findByNickname(userDetails.getUserNickname())
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));
        if (!articleQueryService.existsById(articleId)) {
            throw new RestApiException(CommonExceptionCode.ARTICLE_NOT_FOUND);
        }
        final Scrap scrap = Scrap.create(user, articleId);
        try {
            scrapRepository.save(scrap);
        } catch (DataIntegrityViolationException e) {
            throw new RestApiException(CommonExceptionCode.ALREADY_SCRAP_ARTICLE);
        }
    }

    @Override
    public void delete(final CustomUserDetails userDetails, final String articleId) {
        this.checkUserDetails(userDetails);
        scrapRepository.deleteByUser_NicknameAndArticleId(userDetails.getNickname(), articleId);
    }

    private void checkUserDetails(final CustomUserDetails userDetails) {
        if (Objects.isNull(userDetails)) {
            throw new RestApiException(CommonExceptionCode.UNAUTHORIZED);
        }
    }

}
