package darkoverload.itzip.feature.techinfo.util;

import darkoverload.itzip.feature.techinfo.type.SortType;
import org.springframework.data.domain.Sort;

/**
 * 포스트 정렬 방식에 따라 Spring Data JPA의 Sort 객체를 생성하는 유틸리티 클래스.
 * 인스턴스화될 필요 없이 정적 메서드로만 사용됨.
 */
public class SortUtil {

    /**
     * 유틸리티 클래스이므로 인스턴스화 방지를 위한 private 생성자.
     */
    private SortUtil() {
        // 인스턴스화 방지: 유틸리티 클래스는 모든 메서드가 정적이므로 인스턴스화될 필요가 없음
    }

    /**
     * 주어진 SortType에 따라 적절한 정렬 방식을 반환.
     *
     * @param sortType 정렬 방식 (NEWEST, OLDEST, VIEWCOUNT, LIKECOUNT)
     * @return Sort 객체, 정렬 기준 필드를 포함함
     */
    public static Sort getSort(SortType sortType) {
        switch (sortType) {
            case VIEWCOUNT:
                return Sort.by(Sort.Direction.DESC, "view_count"); // 조회수 기준 내림차순
            case LIKECOUNT:
                return Sort.by(Sort.Direction.DESC, "like_count"); // 좋아요 수 기준 내림차순
            case OLDEST:
                return Sort.by(Sort.Direction.ASC, "create_date"); // 작성일 기준 오름차순
            case NEWEST:
            default:
                return Sort.by(Sort.Direction.DESC, "create_date"); // 작성일 기준 내림차순
        }
    }
}