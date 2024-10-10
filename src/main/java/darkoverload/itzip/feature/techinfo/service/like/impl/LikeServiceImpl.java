package darkoverload.itzip.feature.techinfo.service.like.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.repository.like.LikeRepository;
import darkoverload.itzip.feature.techinfo.repository.like.cache.LikeCacheRepository;
import darkoverload.itzip.feature.techinfo.service.like.LikeService;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final LikeCacheRepository likeCacheRepository;

    @Override
    @Transactional(readOnly = true) // PostgreSQL과 관련된 작업에 트랜잭션 적용
    public boolean toggleLike(CustomUserDetails userDetails, String postId) {
        Long userId = getUserIdByEmail(userDetails.getEmail());

        boolean isLiked = isLiked(userId, postId); // 현재 상태 확인
        likeCacheRepository.setLikeStatus(userId, postId, !isLiked, 90); // 상태를 반전하여 설정

        return !isLiked;
    }

    @Override
    public boolean isLiked(Long userId, String postId) {
        Boolean isLiked = likeCacheRepository.getLikeStatus(userId, postId); // 캐시 확인

        if (isLiked == null) { // 캐시에 없으면 DB 조회
            isLiked = likeRepository.existsByUserIdAndPostId(userId, new ObjectId(postId));
            likeCacheRepository.setLikeStatus(userId, postId, isLiked, 90); // 캐시 저장
        }

        return isLiked;
    }

    private Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserEntity::convertToDomain)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                )
                .getId();
    }
}