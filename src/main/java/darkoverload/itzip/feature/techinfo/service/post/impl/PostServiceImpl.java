package darkoverload.itzip.feature.techinfo.service.post.impl;

import darkoverload.itzip.feature.techinfo.controller.post.request.PostCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.request.PostUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostDetailInfoResponse;
import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.domain.Post;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.repository.post.PostRepository;
import darkoverload.itzip.feature.techinfo.service.blog.core.BlogSearchService;
import darkoverload.itzip.feature.techinfo.service.like.LikeService;
import darkoverload.itzip.feature.techinfo.service.post.PostService;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.service.scrap.ScrapService;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.bson.types.ObjectId;

import java.util.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BlogSearchService blogService;
    private final LikeService likeService;
    private final ScrapService scrapService;

    @Override
    @Transactional(readOnly = true) // PostgreSQL과 관련된 작업에 트랜잭션 적용
    public void addNewPost(CustomUserDetails userDetails, PostCreateRequest request) {
        Long userId = getUserIdByEmail(userDetails.getEmail()); // 로그인 회원 이메일을 통해 유저 ID 조회
        Long blogId = blogService.findBlogSearchById(userId).getId(); // 블로그 ID 조회

        Post post = Post.createPost(request, blogId); // 새로운 포스트 생성
        postRepository.save(post.convertToDocumentWithoutPostId()); // 포스트 저장
    }

    @Override
    public void modifyPost(PostUpdateRequest request) {
        boolean isUpdated = postRepository.updatePost( // 포스트 업데이트
                new ObjectId(request.getPostId()), // 포스트 ID
                new ObjectId(request.getCategoryId()), // 카테고리 ID
                request.getTitle(), // 포스트 제목
                request.getContent(), // 포스트 내용
                request.getThumbnailImagePath(), // 썸네일 이미지 경로
                request.getContentImagePaths() // 내용 이미지 경로
        );
        validateUpdate(isUpdated, CommonExceptionCode.NOT_FOUND_POST); // 업데이트 실패 시 예외 처리
    }

    @Override
    public void hidePost(String postId) {
        boolean isUpdated = postRepository.updatePostVisibility(
                new ObjectId(postId),
                false // 포스트 비공개 처리
        );
        validateUpdate(isUpdated, CommonExceptionCode.NOT_FOUND_POST); // 실패 시 예외 처리
    }

    @Override
    @Transactional(readOnly = true) // PostgreSQL과 관련된 작업에 트랜잭션 적용
    public PostDetailInfoResponse getPostDetailById(CustomUserDetails userDetails, String postId) {
        Long loggedInUserId = getUserIdByEmail(userDetails.getEmail()); // 현재 로그인한 사용자 정보 조회
        boolean isLiked = likeService.isLiked(loggedInUserId, postId); // 좋아요 상태 확인
        boolean isScrapped = scrapService.isScrapped(loggedInUserId, postId); // 스크랩 상태 확인

        Post post = getByPostIdExcludingIsPublic(postId) // 포스트 ID로 포스트 세부 정보 조회
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_POST)
                ); // 포스트가 없으면 예외 발생

        postRepository.updateViewCount(new ObjectId(postId)); // 조회수 증가
        Blog blog = blogService.findBlogSearchById(post.getBlogId()); // 블로그 정보 조회

        return post.convertToPostDetailInfoResponse(blog.getUser(), isLiked, isScrapped); // 포스트 세부 정보 반환
    }

    private void validateUpdate(boolean isUpdated, CommonExceptionCode exceptionCode) {
        if (!isUpdated) {
            throw new RestApiException(exceptionCode); // 업데이트 실패 시 예외 발생
        }
    }

    private Optional<Post> getByPostIdExcludingIsPublic(String postId) {
        return postRepository.findByIdExcludingIsPublic(new ObjectId(postId)) // 공개 여부 제외한 포스트 조회
                .map(PostDocument::convertToDomain);
    }

    private Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email) // 이메일로 사용자 정보 조회
                .map(UserEntity::convertToDomain)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER))
                .getId();
    }
}