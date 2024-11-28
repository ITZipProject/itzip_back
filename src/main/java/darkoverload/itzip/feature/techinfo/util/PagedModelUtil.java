package darkoverload.itzip.feature.techinfo.util;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

/**
 * 페이징된 모델(PagedModel) 생성을 위한 유틸리티 클래스.
 */
public class PagedModelUtil {

    /**
     * 유틸리티 클래스의 인스턴스화를 방지하기 위한 private 생성자.
     */
    private PagedModelUtil() {
    }

    /**
     * Page 객체로부터 PagedModel 을 생성합니다.
     *
     * @param page 변환할 Page 객체
     * @param <T>  페이지 내 요소의 타입
     * @return 생성된 PagedModel 객체
     */
    public static <T> PagedModel<EntityModel<T>> create(Page<T> page) {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                page.getSize(),
                page.getNumber(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return PagedModel.of(
                page.stream()
                        .map(EntityModel::of)
                        .toList(),
                pageMetadata
        );
    }

}
