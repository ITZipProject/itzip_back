package darkoverload.itzip.feature.techinfo.service.post.impl;

import darkoverload.itzip.feature.techinfo.controller.post.response.PostPreviewResponse;
import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.domain.Post;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.repository.post.PostRepository;
import darkoverload.itzip.feature.techinfo.service.blog.core.BlogSearchService;
import darkoverload.itzip.feature.techinfo.service.post.PostPreviewService;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.feature.techinfo.util.PagedModelUtil;
import darkoverload.itzip.feature.techinfo.util.SortUtil;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostPreviewServiceImpl implements PostPreviewService {

    private final PostRepository postRepository;
    private final BlogSearchService blogService;

    @Override
    public PagedModel<EntityModel<PostPreviewResponse>> getAllOrFilteredPosts(
            Optional<String> categoryId, SortType sortType, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, SortUtil.getSort(sortType)); // 페이지와 정렬 설정

        List<Post> posts = categoryId
                .map(id -> getPostsByCategoryId(id, pageable)) // 카테고리별 게시물 조회
                .orElseGet(() -> getAllPosts(pageable)); // 모든 게시물 조회

        if (posts.isEmpty()) {
            throw new RestApiException(
                    categoryId.isPresent() ? CommonExceptionCode.NOT_FOUND_POSTS_FOR_CATEGORY : CommonExceptionCode.NOT_FOUND_POST
            );
        }

        List<PostPreviewResponse> previewResponses = posts.stream()
                .map(this::convertToPostPreviewResponse) // 게시물 응답 변환
                .toList();

        Page<PostPreviewResponse> postPreviewPage = new PageImpl<>(previewResponses, pageable, previewResponses.size()); // 페이지 생성

        return PagedModelUtil.createPagedResponse(previewResponses, postPreviewPage, pageable); // 페이징된 응답 반환
    }

    private List<Post> getPostsByCategoryId(String categoryId, Pageable pageable) {
        return postRepository.findPostsByCategoryId(new ObjectId(categoryId), pageable) // 카테고리별 게시물 조회
                .getContent()
                .stream()
                .map(PostDocument::convertToDomainWithoutViewCount)
                .toList();
    }

    private List<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAllPosts(pageable) // 모든 게시물 조회
                .getContent()
                .stream()
                .map(PostDocument::convertToDomainWithoutViewCount)
                .toList();
    }

    private PostPreviewResponse convertToPostPreviewResponse(Post post) {
        Blog blog = blogService.findBlogSearchById(post.getBlogId()); // 블로그 정보 조회
        return post.convertToPostPreviewResponse(blog.getUser());
    }
}