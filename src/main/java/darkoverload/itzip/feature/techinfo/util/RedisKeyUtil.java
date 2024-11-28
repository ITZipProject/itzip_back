package darkoverload.itzip.feature.techinfo.util;

/**
 * Redis 키 생성을 위한 유틸리티 클래스.
 */
public class RedisKeyUtil {

    /**
     * 유틸리티 클래스의 인스턴스화를 방지하기 위한 private 생성자.
     */
    private RedisKeyUtil() {
    }

    /**
     * 사용자 ID, 포스트 ID, 키 접미사를 조합하여 Redis 키를 생성합니다.
     *
     * @param userId    사용자 ID
     * @param postId    포스트 ID
     * @param keySuffix 키 접미사 (예: "like", "scrap" 등)
     * @return 생성된 Redis 키 문자열
     */
    public static String buildRedisKey(Long userId, String postId, String keySuffix) {
        return "post:" + postId + ":user:" + userId + ":" + keySuffix;
    }

}
