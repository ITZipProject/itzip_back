package darkoverload.itzip.feature.techinfo.service.scrap.impl;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.repository.scrap.ScrapRepository;
import darkoverload.itzip.feature.techinfo.repository.scrap.cache.ScrapCacheRepository;
import darkoverload.itzip.feature.techinfo.service.scrap.ScrapService;
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
public class ScrapServiceImpl implements ScrapService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final ScrapCacheRepository scrapCacheRepository;

    @Override
    @Transactional(readOnly = true) // PostgreSQL과 관련된 작업에 트랜잭션 적용
    public boolean toggleScrap(CustomUserDetails userDetails, String postId) {
        Long userId = getUserIdByEmail(userDetails.getEmail());

        boolean isScrapped = isScrapped(userId, postId); // 현재 상태 확인
        scrapCacheRepository.setScrapStatus(userId, postId, !isScrapped, 90); // 상태를 반전하여 서렂ㅇ

        return !isScrapped;
    }

    @Override
    public boolean isScrapped(Long userId, String postId) {
        Boolean isScrapped = scrapCacheRepository.getScrapStatus(userId, postId); // 캐시 확인

        if (isScrapped == null) {
            isScrapped = scrapRepository.existsByUserIdAndPostId(userId, new ObjectId(postId));
            scrapCacheRepository.setScrapStatus(userId, postId, isScrapped, 90); // 캐시 저장
        }

        return isScrapped;
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