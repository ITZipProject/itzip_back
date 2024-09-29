package darkoverload.itzip.feature.techinfo.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.List;

public class PagedModelUtil {

    /**
     * 유틸리티 클래스이므로 인스턴스화 방지를 위한 private 생성자.
     */
    private PagedModelUtil() {
        // 인스턴스화 방지: 유틸리티 클래스는 모든 메서드가 정적이므로 인스턴스화될 필요가 없음
    }

    public static <T> PagedModel<EntityModel<T>> createPagedResponse(List<T> responses, Page<T> page, Pageable pageable) {

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                pageable.getPageSize(),
                pageable.getPageNumber(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return PagedModel.of(
                responses.stream()
                        .map(EntityModel::of)
                        .toList(),
                pageMetadata
        );
    }
}
