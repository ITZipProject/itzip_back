package darkoverload.itzip.feature.techinfo.type;

/**
 * 포스트 정렬 방식에 대한 열거형(Enum).
 * 포스트를 최신순, 오래된 순, 조회수 순, 좋아요 순으로 정렬할 때 사용됨.
 */
public enum SortType {

    /**
     * 최신순으로 정렬.
     */
    NEWEST,

    /**
     * 오래된 순으로 정렬.
     */
    OLDEST,

    /**
     * 조회수 순으로 정렬.
     */
    VIEWCOUNT,

    /**
     * 좋아요 순으로 정렬.
     */
    LIKECOUNT
}