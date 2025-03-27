package darkoverload.itzip.feature.techinfo.application.service.query.impl;

import darkoverload.itzip.feature.techinfo.application.generator.PageableGenerator;
import darkoverload.itzip.feature.techinfo.ui.payload.response.CommentResponse;
import darkoverload.itzip.feature.techinfo.application.service.query.CommentQueryService;
import darkoverload.itzip.feature.techinfo.application.type.SortType;
import darkoverload.itzip.feature.techinfo.domain.entity.Comment;
import darkoverload.itzip.feature.techinfo.domain.repository.CommentRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentRepository commentRepository;

    public CommentQueryServiceImpl(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Page<CommentResponse> getCommentsByArticleId(final String articleId, final int page, final int size) {
        final Pageable pageable = PageableGenerator.generate(page, size, SortType.NEWEST);
        final Page<Comment> comments = commentRepository.findAllByArticleId(articleId, pageable);
        if (comments.isEmpty()) {
            throw new RestApiException(CommonExceptionCode.COMMENT_NOT_FOUND);
        }
        return comments.map(CommentResponse::from);
    }

}
