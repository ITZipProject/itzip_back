package darkoverload.itzip.feature.techinfo.service.comment.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.request.CommentCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.request.CommentUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.response.CommentResponse;
import darkoverload.itzip.feature.techinfo.domain.Comment;
import darkoverload.itzip.feature.techinfo.model.document.CommentDocument;
import darkoverload.itzip.feature.techinfo.repository.comment.CommentRepository;
import darkoverload.itzip.feature.techinfo.service.comment.CommentService;
import darkoverload.itzip.feature.techinfo.util.PagedModelUtil;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

import org.bson.types.ObjectId;
import org.springframework.data.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true) // PostgreSQL과 관련된 작업에 트랜잭션 적용
    public void addNewComment(CustomUserDetails userDetails, CommentCreateRequest request) {
        Long userId = getUserIdByEmail(userDetails.getEmail()); // 유저 ID 조회

        Comment comment = Comment.createComment(request, userId); // 댓글 생성
        commentRepository.save(comment.convertToDocumentWithoutCommentId()); // 댓글 저장
    }

    @Override
    @Transactional(readOnly = true) // PostgreSQL과 관련된 작업에 트랜잭션 적용
    public void modifyComment(CustomUserDetails userDetails, CommentUpdateRequest request) {
        Long userId = getUserIdByEmail(userDetails.getEmail()); // 유저 ID 조회

        boolean isUpdated = commentRepository.updateComment(
                new ObjectId(request.getCommentId()),
                userId,
                request.getContent()
        ); // 댓글 업데이트

        if (!isUpdated) {
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_COMMENT); // 댓글이 없으면 예외 발생
        }
    }

    @Override
    @Transactional(readOnly = true) // PostgreSQL과 관련된 작업에 트랜잭션 적용
    public void hideComment(CustomUserDetails userDetails, String commentId) {
        Long userId = getUserIdByEmail(userDetails.getEmail()); // 유저 ID 조회

        boolean isUpdated = commentRepository.updateCommentVisibility(
                new ObjectId(commentId), userId, false); // 댓글 숨김 처리

        if (!isUpdated) {
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_COMMENT); // 숨김 처리 실패 시 예외 발생
        }
    }

    @Override
    @Transactional(readOnly = true) // PostgreSQL과 관련된 작업에 트랜잭션 적용
    public PagedModel<EntityModel<CommentResponse>> getFilteredComments(String postId, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "create_date"); // 최신순 정렬
        Pageable pageable = PageRequest.of(page, size, sort); // 페이지 설정

        List<Comment> comments = getCommentsByPost(postId, pageable); // 댓글 목록 조회
        List<CommentResponse> commentResponses = comments.stream()
                .map(this::convertToCommentResponse) // 댓글 응답 변환
                .toList();

        Page<CommentResponse> commentPage = new PageImpl<>(commentResponses, pageable, commentResponses.size()); // 페이지 생성

        return PagedModelUtil.createPagedResponse(commentResponses, commentPage, pageable); // 페이징 응답 반환
    }

    private List<Comment> getCommentsByPost(String postId, Pageable pageable) {
        return commentRepository.findCommentsByPostId(new ObjectId(postId), pageable)
                .getContent().stream()
                .map(CommentDocument::convertToDocumentWithoutPostId) // 도메인 변환
                .toList();
    }

    private CommentResponse convertToCommentResponse(Comment comment) {
        User user = userRepository.findById(comment.getUserId())
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                )
                .convertToDomain(); // 유저 정보 조회

        return comment.convertToCommentResponse(user); // 댓글 응답 생성
    }

    private Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserEntity::convertToDomain)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                )
                .getId();
    }
}
