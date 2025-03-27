package darkoverload.itzip.feature.techinfo.application.service.command.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.service.command.CommentCommandService;
import darkoverload.itzip.feature.techinfo.application.service.query.ArticleQueryService;
import darkoverload.itzip.feature.techinfo.domain.entity.Comment;
import darkoverload.itzip.feature.techinfo.domain.repository.CommentRepository;
import darkoverload.itzip.feature.techinfo.ui.payload.request.comment.CommentEditRequest;
import darkoverload.itzip.feature.techinfo.ui.payload.request.comment.CommentRegistrationRequest;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private final ArticleQueryService articleQueryService;

    @Override
    public void create(final CustomUserDetails userDetails, final CommentRegistrationRequest request) {
        this.checkUserDetails(userDetails);
        final UserEntity user = userRepository.findByNickname(userDetails.getUserNickname())
                        .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));
        if (!articleQueryService.existsById(request.articleId())) {
            throw new RestApiException(CommonExceptionCode.ARTICLE_NOT_FOUND);
        }
        final Comment comment = Comment.create(user, request.articleId(), request.content());
        commentRepository.save(comment);
    }

    @Override
    public void update(final CustomUserDetails userDetails, final CommentEditRequest request) {
        this.checkUserDetails(userDetails);
        final Comment comment = commentRepository.findByIdAndUser_Nickname(request.commentId(), userDetails.getUserNickname())
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.COMMENT_NOT_FOUND));
        comment.updateContent(request.content());
    }

    @Override
    public void delete(final CustomUserDetails userDetails, final Long commentId) {
        this.checkUserDetails(userDetails);
        final Comment comment = commentRepository.findByIdAndUser_Nickname(commentId, userDetails.getUserNickname())
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.COMMENT_NOT_FOUND));
        comment.hide();
    }

    @Override
    public void deleteByArticleId(final String articleId) {
        commentRepository.setDisplayedFalseByArticleId(articleId);
    }

    private void checkUserDetails(final CustomUserDetails userDetails) {
        if (Objects.isNull(userDetails)) {
            throw new RestApiException(CommonExceptionCode.UNAUTHORIZED);
        }
    }

}
