package darkoverload.itzip.feature.techinfo.service.like.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.repository.like.LikeRepository;
import darkoverload.itzip.feature.techinfo.repository.like.cache.LikeCacheRepository;
import darkoverload.itzip.feature.techinfo.service.like.LikeService;

import darkoverload.itzip.feature.techinfo.service.shared.SharedService;
import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeCacheRepository likeCacheRepository;
    private final SharedService sharedService;

    @Override
    @Transactional
    public boolean toggleLike(CustomUserDetails userDetails, String postId) {
        Long userId = sharedService.getUserByEmail(userDetails.getEmail()).getId();

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
}