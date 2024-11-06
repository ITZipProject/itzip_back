package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.algorithm.controller.response.SolvedUserResponse;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//사용자 정보를 보내주는 응답
@Service
@RequiredArgsConstructor
public class FindUserSolvedProfileServiceImpl implements FindUserSolvedProfileService {
    private final SolvedacUserRepository solvedacUserRepository;

    /**
     * solvedac사용자 정보를 보내주는 메서드
     * @param customUserDetails 사용자 인증 정보
     * @return 사용자 정보를 담은 객체
     */
    @Override
    public SolvedUserResponse findUserSolvedProfile(CustomUserDetails customUserDetails) {
        return SolvedUserResponse.builder()
                .solvedacUser(solvedacUserRepository.findByUserEntityEmail(customUserDetails.getEmail()).orElseThrow(() ->
                        new RestApiException(CommonExceptionCode.NOT_FOUND_SOLVEDAC_USER)
                ).convertToDomain())
                .build();
    }
}
