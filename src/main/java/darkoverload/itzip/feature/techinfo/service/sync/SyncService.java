package darkoverload.itzip.feature.techinfo.service.sync;

import darkoverload.itzip.feature.techinfo.domain.Like;
import darkoverload.itzip.feature.techinfo.dto.like.LikeStatusDto;
import darkoverload.itzip.feature.techinfo.repository.like.LikeRepository;
import darkoverload.itzip.feature.techinfo.repository.like.cache.LikeCacheRepository;
import darkoverload.itzip.feature.techinfo.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 좋아요 상태를 캐시에서 MongoDB로 동기화하는 서비스.
 * 캐시에 저장된 좋아요 상태를 주기적으로 데이터베이스에 반영.
 */
@Service
@RequiredArgsConstructor
public class SyncService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final LikeCacheRepository likeCacheRepository;

    @Scheduled(fixedRate = 60000) // 60초마다 캐시된 좋아요 상태를 MongoDB로 동기화
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