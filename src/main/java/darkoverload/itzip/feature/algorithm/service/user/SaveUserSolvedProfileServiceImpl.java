package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.algorithm.util.SaveSolvedUser;
import darkoverload.itzip.feature.algorithm.util.SaveUserSolvedProblem;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.service.UserService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//처음 사용자가 solvedac username을 등록했을때 사용할 class
@Service
@RequiredArgsConstructor
public class SaveUserSolvedProfileServiceImpl implements SaveUserSolvedProfileService {
    private final SaveSolvedUser saveSolvedUser;
    private final SaveUserSolvedProblem saveUserSolvedProblem;

    private final UserService userService;

    /**
     * 사용자 id와 이름을 받아와서 solvedAc를 등록하는 메서드
     * @param customUserDetails 사용자 인증 정보
     * @param username 사용자 sovledac 이름
     */
    @Override
    @Transactional
    public void saveUserSolvedProfile(CustomUserDetails customUserDetails, String username) {
        User user = userService.findByEmail(customUserDetails.getEmail()).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
        );

        saveSolvedUser.saveSolvedUser(user.getId(), username);
        //사용자가 푼 문제들 저장
        saveUserSolvedProblem.saveUserSolvedProblem(user.getId());
    }
}
