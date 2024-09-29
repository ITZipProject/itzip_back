package darkoverload.itzip.feature.techinfo.service.like.impl;

import darkoverload.itzip.feature.techinfo.repository.like.LikeRepository;
import darkoverload.itzip.feature.techinfo.repository.like.cache.LikeCacheRepository;
import darkoverload.itzip.feature.techinfo.service.like.LikeService;

import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeCacheRepository likeCacheRepository;

    @Override
    @Transactional
    public boolean toggleLike(Long userId, String postId) {
        boolean isLiked = isLiked(userId, postId); // 현재 상태 확인

        if (isLiked) {
            likeCacheRepository.setLikeStatus(userId, postId, false, 90); // 좋아요 취소
            return false;
        }

        likeCacheRepository.setLikeStatus(userId, postId, true, 90); // 좋아요 추가
        return true;
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