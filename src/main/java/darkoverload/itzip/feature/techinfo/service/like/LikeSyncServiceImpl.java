package darkoverload.itzip.feature.techinfo.service.like;

import darkoverload.itzip.feature.techinfo.domain.like.Like;
import darkoverload.itzip.feature.techinfo.dto.like.LikeStatus;
import darkoverload.itzip.feature.techinfo.service.like.port.LikeCacheRepository;
import darkoverload.itzip.feature.techinfo.service.like.port.LikeRepository;
import darkoverload.itzip.feature.techinfo.service.post.PostCommandService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 좋아요 동기화 서비스 구현 클래스.
 * 캐시된 좋아요 정보를 MongoDB에 주기적으로 동기화합니다.
 */
@Service
@RequiredArgsConstructor
public class LikeSyncServiceImpl implements LikeSyncService {

    private final LikeRepository likeRepository;
    private final LikeCacheRepository likeCacheRepository;
    private final PostCommandService postCommandService;

    /**
     * 캐시된 좋아요 정보를 MongoDB에 동기화합니다.
     * 이 메소드는 설정된 스케줄에 따라 주기적으로 실행됩니다.
     */
    @Override
    @Scheduled(cron = "${TECHINFO_LIKE_SCHEDULER_CRON}")
    public void persistLikesToMongo() {
        List<LikeStatus> cachedLikes = likeCacheRepository.getAllLikeStatuses();

        for (LikeStatus likeStatus : cachedLikes) {
            String postId = likeStatus.getPostId();
            Long userId = likeStatus.getUserId();
            boolean isLiked = likeStatus.getIsLiked();
            boolean exists = likeRepository.existsByUserIdAndPostId(userId, new ObjectId(postId));

            if (isLiked && !exists) {
                Like like = Like.from(likeStatus);
                likeRepository.save(like);
                postCommandService.updateFieldWithValue(postId, "like_count", 1);
            } else if (!isLiked && exists) {
                likeRepository.deleteByUserIdAndPostId(userId, new ObjectId(postId));
                postCommandService.updateFieldWithValue(postId, "like_count", -1);
            }
        }
    }

}
