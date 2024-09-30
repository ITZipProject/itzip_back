package darkoverload.itzip.feature.techinfo.repository.like.cache;

import darkoverload.itzip.feature.techinfo.dto.like.LikeStatusDto;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class LikeCacheRepositoryImpl implements LikeCacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    // 캐시에 특정 유저의 포스트에 대한 좋아요 상태를 저장
    @Override
    public void setLikeStatus(Long userId, String postId, boolean isLiked, long ttl) {
        String redisKey = buildRedisKey(userId, postId); // Redis 키 생성
        redisTemplate.opsForValue().set(redisKey, String.valueOf(isLiked), ttl, TimeUnit.MINUTES); // 좋아요 상태와 TTL 설정
    }

    // 캐시에서 특정 유저의 포스트에 대한 좋아요 상태를 조회
    @Override
    public Boolean getLikeStatus(Long userId, String postId) {
        String redisKey = buildRedisKey(userId, postId); // Redis 키 생성
        String isLikedString = (String) redisTemplate.opsForValue().get(redisKey); // Redis에서 좋아요 상태를 문자열로 조회

        // 문자열을 Boolean으로 변환, null 처리 포함
        return isLikedString != null ? Boolean.valueOf(isLikedString) : null;
    }

    // 캐시에서 모든 좋아요 상태를 조회
    @Override
    public List<LikeStatusDto> getAllLikeStatuses() {
        List<LikeStatusDto> likeStatuses = new ArrayList<>(); // 좋아요 상태를 저장할 리스트 생성

        Set<String> keys = redisTemplate.keys("post:*:user:*:like"); // 모든 좋아요 키 조회

        // 각 키에 대해 좋아요 상태를 가져와 리스트에 추가
        for (String key : keys) {
            String isLikedString = (String) redisTemplate.opsForValue().get(key); // Redis에서 좋아요 상태를 문자열로 조회

            if (isLikedString != null) { // 좋아요 상태가 null이 아니면 처리
                Boolean isLiked = Boolean.valueOf(isLikedString); // 문자열을 Boolean으로 변환
                String[] parts = key.split(":"); // 키를 ":"로 분할하여 포스트ID와 유저ID 추출
                String postId = parts[1]; // 포스트ID 추출
                Long userId = Long.valueOf(parts[3]); // 유저ID 추출

                // LikeStatusDto 객체 생성 후 리스트에 추가
                likeStatuses.add(LikeStatusDto.builder()
                        .userId(userId)
                        .postId(postId)
                        .isLiked(isLiked)
                        .build()
                );
            }
        }

        return likeStatuses; // 좋아요 상태 리스트 반환
    }

    // Redis 키를 생성하는 메서드
    private String buildRedisKey(Long userId, String postId) {
        return "post:" + postId + ":user:" + userId + ":like"; // "post:포스트ID:user:유저ID:like" 형식의 키 생성
    }
}