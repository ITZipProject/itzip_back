package darkoverload.itzip.feature.techinfo.util;

/**
 * RedisKeyUtil은 Redis 키 생성을 위한 유틸리티 클래스이다.
 * 이 클래스는 포스트 ID와 사용자 ID를 결합하여 Redis 키를 생성하는 기능을 제공한다.
 */
public class RedisKeyUtil {

    /**
     * 유틸리티 클래스이므로 인스턴스화 방지를 위한 private 생성자.
     */
    private RedisKeyUtil() {
        // 인스턴스화 방지: 유틸리티 클래스는 모든 메서드가 정적이므로 인스턴스화될 필요가 없음
    }

    /**
     * 포스트 ID와 사용자 ID를 사용하여 Redis 키를 생성한다.
     *
     * @param userId 사용자의 ID
     * @param postId 포스트의 ID
     * @param keySuffix 키의 끝부분(예: "scrap", "like")
     * @return Redis 키 문자열, 형식: "post:{postId}:user:{userId}:{keySuffix}"
     */
    public static String buildRedisKey(Long userId, String postId, String keySuffix) {
        return "post:" + postId + ":user:" + userId + ":" + keySuffix;
    }
}
