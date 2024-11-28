package darkoverload.itzip.feature.techinfo.service.scrap;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.service.scrap.port.ScrapCacheRepository;
import darkoverload.itzip.feature.techinfo.service.scrap.port.ScrapRepository;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 스크랩 관련 서비스 구현 클래스.
 */
@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    private final ScrapRepository scrapRepository;
    private final ScrapCacheRepository scrapCacheRepository;
    private final UserRepository userRepository;

    /**
     * 사용자의 스크랩 상태를 토글합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @param postId 포스트 ID
     * @return 토글 후 스크랩 상태
     * @throws RestApiException 사용자를 찾을 수 없을 때 발생
     */
    @Override
    @Transactional(readOnly = true)
    public boolean toggleScrap(CustomUserDetails userDetails, String postId) {
        Long userId = userRepository.findByEmail(userDetails.getEmail())
                .map(UserEntity::convertToDomain)
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                )
                .getId();

        boolean isScrapped = isScrapped(userId, postId);

        scrapCacheRepository.save(userId, postId, !isScrapped, 90);

        return !isScrapped;
    }

    /**
     * 사용자의 특정 포스트에 대한 스크랩 상태를 확인합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 스크랩 상태
     */
    @Override
    public boolean isScrapped(Long userId, String postId) {
        Boolean isScrapped = scrapCacheRepository.getScrapStatus(userId, postId);

        if (isScrapped == null) {
            isScrapped = scrapRepository.existsByUserIdAndPostId(userId, new ObjectId(postId));
            scrapCacheRepository.save(userId, postId, isScrapped, 90);
        }

        return isScrapped;
    }
    ㅋ
}
