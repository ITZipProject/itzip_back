package darkoverload.itzip.feature.techinfo.service.post.impl;

import darkoverload.itzip.feature.techinfo.controller.post.response.PostBlogPreviewResponse;
import darkoverload.itzip.feature.techinfo.domain.Post;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.repository.post.PostRepository;
import darkoverload.itzip.feature.techinfo.service.blog.facade.BlogFacade;
import darkoverload.itzip.feature.techinfo.service.post.PostBlogPreviewService;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.feature.techinfo.util.PagedModelUtil;
import darkoverload.itzip.feature.techinfo.util.SortUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostBlogPreviewServiceImpl implements PostBlogPreviewService {

    private final PostRepository postRepository;
    private final BlogFacade blogService;

    @Override
    public PagedModel<EntityModel<PostBlogPreviewResponse>> getPostsByBlogId(
            Long blogId, SortType sortType, int page, int size) {

        Long validBlogId = blogService.getBlogById(blogId).getId(); // 유효한 블로그 ID 조회

        Pageable pageable = PageRequest.of(page, size, SortUtil.getSort(sortType)); // 페이지 및 정렬 설정

        List<PostBlogPreviewResponse> previewResponses = postRepository.findPostsByBlogId(validBlogId, pageable) // 블로그 ID로 포스트 조회
                .getContent().stream()
                .map(PostDocument::convertToDomainWithoutBlodIdAndViewCount)
                .map(Post::convertToBlogPreviewResponse)
                .toList();

        Page<PostBlogPreviewResponse> postBlogPreviewPage = new PageImpl<>(previewResponses, pageable, previewResponses.size()); // 페이지 생성

        return PagedModelUtil.createPagedResponse(previewResponses, postBlogPreviewPage, pageable); // 페이지네이션된 응답 반환
    }
}