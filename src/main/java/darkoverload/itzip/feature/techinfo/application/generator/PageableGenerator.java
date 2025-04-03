package darkoverload.itzip.feature.techinfo.application.generator;

import darkoverload.itzip.feature.techinfo.application.type.SortType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 페이지와 정렬 옵션에 따른 Pageable 객체를 생성하는 유틸리티 클래스입니다.
 */
public class PageableGenerator {

    private static final String FIELD_VIEW_COUNT = "viewCount";
    private static final String FIELD_LIKE_COUNT = "likesCount";
    private static final String FIELD_CREATED_AT = "createdAt";

    private PageableGenerator() {
    }

    public static Pageable generate(final int page, final int size, final SortType type) {
        return switch (type) {
            case VIEW_COUNT-> PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, FIELD_VIEW_COUNT));
            case LIKE_COUNT -> PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, FIELD_LIKE_COUNT));
            case OLDEST -> PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, FIELD_CREATED_AT));
            default -> PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, FIELD_CREATED_AT));
        };
    }

}
