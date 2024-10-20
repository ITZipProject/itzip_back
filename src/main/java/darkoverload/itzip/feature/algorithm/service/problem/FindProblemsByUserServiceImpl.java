package darkoverload.itzip.feature.algorithm.service.problem;

import darkoverload.itzip.feature.algorithm.controller.response.ProblemListResponse;
import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.repository.problem.ProblemRepository;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

//사용자의 tier 기준으로 문제를 추천해주는 서비스
@Service
@RequiredArgsConstructor
public class FindProblemsByUserServiceImpl implements FindProblemsByUserService {
    private final ProblemRepository problemRepository;
    private final SolvedacUserRepository solvedacUserRepository;

    /**
     * 사용자 아이디를 받아와서 문제를 추천해주는 로직
     * @param customUserDetails 사용자 인증 정보
     * @return 문제 응답 객체
     */
    @Override
    public ProblemListResponse findProblemsByUser(CustomUserDetails customUserDetails) {
        //사용자 정보 찾기
        SolvedacUserEntity solvedacUserEntity = solvedacUserRepository.findByUserEntityEmail(customUserDetails.getEmail()).orElseThrow(() ->
                new RestApiException(CommonExceptionCode.NOT_FOUND_SOLVEDAC_USER)
        );
        //사용자 tier 불러오기
        int tier = solvedacUserEntity.convertToDomain().getTier();
        return ProblemListResponse.builder()
                .problems(problemRepository.findProblemsByUser(solvedacUserEntity.getUserId(), tier, PageRequest.of(0, 10)).stream()
                        .map(ProblemEntity::convertToDomain)
                        .toList())
                .build();
    }
}
