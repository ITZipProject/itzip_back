package darkoverload.itzip.feature.techinfo.application.service.command.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.event.payload.LikeCancelledEvent;
import darkoverload.itzip.feature.techinfo.application.event.payload.LikedEvent;
import darkoverload.itzip.feature.techinfo.application.service.command.LikeCommandService;
import darkoverload.itzip.feature.techinfo.application.service.query.ArticleQueryService;
import darkoverload.itzip.feature.techinfo.domain.entity.Like;
import darkoverload.itzip.feature.techinfo.domain.repository.LikeRepository;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeCommandServiceImpl implements LikeCommandService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    private final ArticleQueryService articleQueryService;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void create(final CustomUserDetails userDetails, final String articleId) {
        this.checkUserDetails(userDetails);
        final UserEntity user = userRepository.findByNickname(userDetails.getNickname())
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));
        if (!articleQueryService.existsById(articleId)) {
            throw new RestApiException(CommonExceptionCode.ARTICLE_NOT_FOUND);
        }
        final Like like = Like.create(user, articleId);
        try {
            likeRepository.save(like);
        } catch (DataIntegrityViolationException e) {
            throw new RestApiException(CommonExceptionCode.ALREADY_LIKED_ARTICLE);
        }
        eventPublisher.publishEvent(new LikedEvent(articleId));
    }

    @Override
    public void delete(final CustomUserDetails userDetails, final String articleId) {
        this.checkUserDetails(userDetails);
        likeRepository.deleteByUser_NicknameAndArticleId(userDetails.getNickname(), articleId);
        eventPublisher.publishEvent(new LikeCancelledEvent(articleId));
    }

    private void checkUserDetails(final CustomUserDetails userDetails) {
        if (Objects.isNull(userDetails)) {
            throw new RestApiException(CommonExceptionCode.UNAUTHORIZED);
        }
    }

}
