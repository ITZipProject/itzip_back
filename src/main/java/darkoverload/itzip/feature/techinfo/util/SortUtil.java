package darkoverload.itzip.feature.techinfo.util;

import darkoverload.itzip.feature.techinfo.type.SortType;
import org.springframework.data.domain.Sort;

/**
 * 정렬 관련 유틸리티 클래스.
 */
public class SortUtil {

    private static final String VIEW_COUNT_FIELD = "view_count";
    private static final String LIKE_COUNT_FIELD = "like_count";
    private static final String CREATE_DATE_FIELD = "create_date";

    /**
     * 유틸리티 클래스의 인스턴스화를 방지하기 위한 private 생성자.
     */
    private SortUtil() {
    }

    /**
     * 주어진 SortType 에 따라 적절한 Sort 객체를 생성합니다.
     *
     * @param sortType 정렬 유형
     * @return 생성된 Sort 객체
     */
    public static Sort getType(SortType sortType) {
        return switch (sortType) {
            case VIEWCOUNT -> Sort.by(Sort.Direction.DESC, VIEW_COUNT_FIELD);
            case LIKECOUNT -> Sort.by(Sort.Direction.DESC, LIKE_COUNT_FIELD);
            case OLDEST -> Sort.by(Sort.Direction.ASC, CREATE_DATE_FIELD);
            default -> Sort.by(Sort.Direction.DESC, CREATE_DATE_FIELD);
        };
    }

}
