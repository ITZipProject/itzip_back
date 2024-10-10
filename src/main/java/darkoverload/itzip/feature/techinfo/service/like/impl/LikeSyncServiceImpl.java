package darkoverload.itzip.feature.techinfo.service.like.impl;

import darkoverload.itzip.feature.techinfo.domain.Like;
import darkoverload.itzip.feature.techinfo.dto.like.LikeStatusDto;
import darkoverload.itzip.feature.techinfo.repository.like.LikeRepository;
import darkoverload.itzip.feature.techinfo.repository.like.cache.LikeCacheRepository;
import darkoverload.itzip.feature.techinfo.repository.post.PostRepository;
import darkoverload.itzip.feature.techinfo.service.like.LikeSyncService;

import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeSyncServiceImpl implements LikeSyncService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final LikeCacheRepository likeCacheRepository;

    @Scheduled(cron = "${TECHINFO_LIKE_SCHEDULER_CRON}")
    @Override
    public void persistLikesToMongo() {
        List<LikeStatusDto> cachedLikes = likeCacheRepository.getAllLikeStatuses(); // 캐시에서 모든 좋아요 상태 조회

        for (LikeStatusDto likeStatus : cachedLikes) {
            ObjectId postId = new ObjectId(likeStatus.getPostId());
            Long userId = likeStatus.getUserId();

            if (likeStatus.getIsLiked()) {
                // 좋아요 추가
                if (!likeRepository.existsByUserIdAndPostId(userId, postId)) {  // 좋아요가 이미 있는지 확인
                    Like like = likeStatus.convertToDomain(); // DTO를 도메인 객체로 변환
                    likeRepository.save(like.convertToDocumentWithoutLikeId()); // 좋아요 추가
                    postRepository.updateLikeCount(postId, 1); // 포스트 좋아요 수 증가
                }
            } else {
                // 좋아요 취소
                if (likeRepository.existsByUserIdAndPostId(userId, postId)) { // 좋아요가 이미 존재하는지 확인
                    likeRepository.deleteByUserIdAndPostId(userId, postId); // 좋아요 취소
                    postRepository.updateLikeCount(postId, -1); // 포스트 좋아요 수 감소
                }
            }
        }
    }
}