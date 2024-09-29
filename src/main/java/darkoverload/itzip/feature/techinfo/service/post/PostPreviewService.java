package darkoverload.itzip.feature.techinfo.service.post;

import darkoverload.itzip.feature.techinfo.controller.response.PostPreviewResponse;
import darkoverload.itzip.feature.techinfo.type.SortType;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

/**
 * 포스트 프리뷰 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
 * 카테고리 필터 및 정렬 타입을 기반으로 포스트 목록을 조회하는 기능을 제공.
 */
public interface PostPreviewService {

    /**
     * 모든 포스트 또는 카테고리로 필터링된 포스트 목록을 조회.
     *
     * @param categoryId 필터링할 카테고리 ID (Optional)
     * @param sortType   정렬 방식 (최신순, 인기순 등)
     * @param page       페이지 번호
     * @param size       페이지당 포스트 개수
     * @return 페이지네이션된 포스트 프리뷰 응답을 담은 PagedModel
     */
    PagedModel<EntityModel<PostPreviewResponse>> getAllOrFilteredPosts(
            Optional<String> categoryId, SortType sortType, int page, int size);
}