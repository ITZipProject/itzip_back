package darkoverload.itzip.feature.algorithm.service.problem;

import darkoverload.itzip.feature.algorithm.controller.response.ProblemListResponse;
import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.repository.problem.ProblemRepository;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.feature.algorithm.util.SolvedTierCalculator;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindProblemsByTagAndUserImpl implements FindProblemsByTagAndUser{
    private final ProblemRepository problemRepository;
    private final SolvedacUserRepository solvedacUserRepository;

    private final SolvedTierCalculator solvedTierCalculator;

    /**
     * 사용자 아이디와 테그 아이드를 받아와서 문제를 추천해주는 로직
     * @param userId 사용자 아이디
     * @param tagId 테그 아이디
     * @return 문제 응답 객체
     */
    @Override
    public ProblemListResponse findProblemsByTagAndUser(Long userId, Long tagId) {
        //사용자 정보 찾기
        SolvedacUserEntity solvedacUserEntity = solvedacUserRepository.findById(userId).orElseThrow(() ->
                new RestApiException(CommonExceptionCode.NOT_FOUND_SOLVEDAC_USER)
        );

        // 사용자 tag 평균 티어 계산
        int userTagRating = userTagAverageRating(userId, tagId);
        //사용자 tier 불러 오기
        int tier = solvedacUserEntity.convertToDomain().getTier();
        int averageTier = (userTagRating + tier) / 2;
        return ProblemListResponse.builder()
                .problems(problemRepository.findProblemsByTagExcludingUserSolved(userId, tagId, averageTier, PageRequest.of(0, 10)).stream()
                        .map(ProblemEntity::convertToDomain)
                        .toList())
                .build();
    }

    /**
     * 사용자가 푼문제를 가져와서 평균적인 tier을 계산하는 메서드
     * @param userId 사용자 id
     * @param tagId 테크 id
     * @return
     */
    private int userTagAverageRating(Long userId, Long tagId) {
        return solvedTierCalculator.tierCalculator(solvedTierCalculator.tagRatingCalculator(problemRepository.findSolvedProblemsByUserAndTag(userId, tagId)
                .stream()
                .map(ProblemEntity::convertToDomain)
                .toList()
        ));
    }
}
