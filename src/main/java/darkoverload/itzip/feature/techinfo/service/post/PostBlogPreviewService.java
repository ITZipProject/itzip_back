package darkoverload.itzip.feature.techinfo.service.post;

import darkoverload.itzip.feature.techinfo.controller.response.PostBlogPreviewResponse;
import darkoverload.itzip.feature.techinfo.type.SortType;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

/**
 * 특정 블로그의 포스트 목록을 조회하는 서비스 인터페이스.
 * 블로그 ID와 정렬 방식에 따라 필터링된 포스트 목록을 반환.
 */
public interface PostBlogPreviewService {

    /**
     * 블로그 ID와 정렬 방식에 따라 포스트 목록을 조회.
     *
     * @param blogId  조회할 블로그의 ID
     * @param sortType  정렬 방식 (최신순, 인기순 등)
     * @param page  페이지 번호
     * @param size  페이지당 포스트 개수
     * @return  페이지네이션된 포스트 목록을 담은 PagedModel
     */
    PagedModel<EntityModel<PostBlogPreviewResponse>> getPostsByBlogId(Long blogId, SortType sortType, int page, int size);
}
