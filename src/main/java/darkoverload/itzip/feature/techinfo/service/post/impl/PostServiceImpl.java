package darkoverload.itzip.feature.techinfo.service.post.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.request.PostCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.request.PostUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.response.BlogAdjacentPostsResponse;
import darkoverload.itzip.feature.techinfo.controller.response.PostDetailInfoResponse;
import darkoverload.itzip.feature.techinfo.controller.response.PostBasicResponse;
import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.domain.Post;
import darkoverload.itzip.feature.techinfo.dto.year.YearlyPostDto;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.repository.post.PostRepository;
import darkoverload.itzip.feature.techinfo.service.shared.SharedService;
import darkoverload.itzip.feature.techinfo.service.like.LikeService;
import darkoverload.itzip.feature.techinfo.service.post.PostService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SharedService sharedService;
    private final LikeService likeService;

    @Override
    public void addNewPost(CustomUserDetails userDetails, PostCreateRequest request) {
        // 사용자의 이메일을 통해 유저 및 블로그 정보 조회 후 새로운 포스트 생성
        Long userId = sharedService.getUserByEmail(userDetails.getEmail()).getId();
        Long blogId = sharedService.getBlogById(userId).getId();

        Post post = Post.createPost(request, blogId);
        postRepository.save(post.convertToDocumentWithoutPostId()); // 포스트 저장
    }

    @Override
    public void modifyPost(PostUpdateRequest request) {
        // 요청 데이터를 기반으로 포스트 업데이트
        boolean isUpdated = postRepository.updatePost(
                new ObjectId(request.getPostId()),
                new ObjectId(request.getCategoryId()),
                request.getTitle(),
                request.getContent(),
                request.getThumbnailImagePath(),
                request.getContentImagePaths()
        );
        validateUpdate(isUpdated, CommonExceptionCode.NOT_FOUND_POST); // 업데이트 실패 시 예외 처리
    }

    @Override
    public void hidePost(String postId) {
        // 포스트 비공개 처리
        boolean isUpdated = postRepository.updatePostVisibility(new ObjectId(postId), false);
        validateUpdate(isUpdated, CommonExceptionCode.NOT_FOUND_POST);
    }

    @Override
    public BlogAdjacentPostsResponse getAdjacentPosts(Long blogId, LocalDateTime createDate) {
        // 지정된 블로그 ID와 생성일을 기준으로 인접한 포스트들을 조회하여 응답 생성
        int limit = 4;
        String nickname = sharedService.getBlogById(blogId).getUser().getNickname();

        // 인접한 포스트들을 조회하고, 각 포스트를 PostBasicResponse로 변환
        List<PostBasicResponse> postResponses = getAdjacentPostsByBlog(blogId, createDate, limit).stream()
                .map(Post::convertToPostBasicResponse)
                .toList();

        // 닉네임과 조회된 포스트들을 포함한 응답 반환
        return BlogAdjacentPostsResponse.builder()
                .nickname(nickname)
                .posts(postResponses)
                .build();
    }

    @Override
    public PostDetailInfoResponse getPostDetailById(CustomUserDetails userDetails, String postId) {
        // 포스트 ID를 통해 포스트 세부 정보 조회
        Post post = getByPostIdExcludingIsPublic(postId)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_POST)); // 포스트가 없으면 예외 발생

        postRepository.updateViewCount(new ObjectId(postId)); // 조회수 증가

        Blog blog = sharedService.getBlogById(post.getBlogId());
        Long currentUserId = sharedService.getUserByNickname(userDetails.getNickname()).getId(); // 현재 사용자 정보
        boolean isLiked = likeService.isLiked(currentUserId, postId); // 좋아요 상태 확인

        return post.convertToPostDetailInfoResponse(blog.getUser(), isLiked); // 포스트 세부 정보 반환
    }

    @Override
    public List<YearlyPostDto> getYearlyPostCounts(Long blogId) {
        // 연도별 포스트 통계 조회
        return postRepository.findYearlyPostCounts(blogId);
    }

    // 업데이트 실패 시 예외 처리
    private void validateUpdate(boolean isUpdated, CommonExceptionCode exceptionCode) {
        if (!isUpdated) {
            throw new RestApiException(exceptionCode);
        }
    }

    // 인접 포스트 조회
    private List<Post> getAdjacentPostsByBlog(Long blogId, LocalDateTime createDate, int limit) {
        return postRepository.findAdjacentPosts(blogId, createDate, limit)
                .stream()
                .map(PostDocument::convertToDomainWithBasicFields)
                .toList();
    }

    // 공개 여부 제외한 포스트 조회
    private Optional<Post> getByPostIdExcludingIsPublic(String postId) {
        return postRepository.findByIdExcludingIsPublic(new ObjectId(postId))
                .map(PostDocument::convertToDomain);
    }
}