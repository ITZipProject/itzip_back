package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.algorithm.domain.SolvedacUser;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.feature.algorithm.util.SaveSolvedUser;
import darkoverload.itzip.feature.algorithm.util.SaveUserSolvedProblem;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateUserSolvedProfileAndProblemImpl implements UpdateUserSolvedProfileAndProblem {
    private final SaveSolvedUser saveSolvedUser;
    private final SaveUserSolvedProblem saveUserSolvedProblem;

    private final SolvedacUserRepository solvedacUserRepository;

    @Value("${SOLVED_AC_USER_UPDATE_COOLDOWN_HOURS}")
    private long updateCooldownHours;
    /**
     * 사용자의 sovledac계정과 username을 업데이트하는 메서드
     * 시간에 제한이 있다.
     */
    @Override
    @Transactional
    public void updateUserSolvedProfileAndProblem(Long userId) {
        SolvedacUser solvedacUser = solvedacUserRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_SOLVEDAC_USER))
                .convertToDomain();

        //사용자가 업데이트 할수 있는 시간인지 확인
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(solvedacUser.getUpdateTime(), currentTime);
        if (duration.toHours() > updateCooldownHours){
            saveSolvedUser.saveSolvedUser(userId, solvedacUser.getUsername());
            //사용자가 푼 문제들 저장
            saveUserSolvedProblem.saveUserSolvedProblem(userId);
        } else {
            throw new RestApiException(CommonExceptionCode.UPDATE_COOLDOWN);
        }
    }
}
