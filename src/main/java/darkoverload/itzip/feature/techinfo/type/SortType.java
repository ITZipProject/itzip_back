package darkoverload.itzip.feature.techinfo.type;

/**
 * 정렬 유형을 나타내는 열거형.
 * 포스트나 기타 항목들을 정렬할 때 사용되는 기준을 정의합니다.
 */
public enum SortType {

    /**
     * 최신순 정렬.
     * 가장 최근에 생성된 항목부터 정렬합니다.
     */
    NEWEST,

    /**
     * 오래된순 정렬.
     * 가장 오래전에 생성된 항목부터 정렬합니다.
     */
    OLDEST,

    /**
     * 조회수순 정렬.
     * 조회수가 높은 항목부터 정렬합니다.
     */
    VIEWCOUNT,

    /**
     * 좋아요순 정렬.
     * 좋아요 수가 많은 항목부터 정렬합니다.
     */
    LIKECOUNT

}
