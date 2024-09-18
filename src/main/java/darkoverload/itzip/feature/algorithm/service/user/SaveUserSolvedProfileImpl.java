package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.algorithm.util.SaveSolvedUser;
import darkoverload.itzip.feature.algorithm.util.SaveUserSolvedProblem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//처음 사용자가 solvedac username을 등록했을때 사용할 class
@Service
@RequiredArgsConstructor
public class SaveUserSolvedProfileImpl implements SaveUserSolvedProfile {
    private final SaveSolvedUser saveSolvedUser;
    private final SaveUserSolvedProblem saveUserSolvedProblem;

    /**
     * 사용자 id와 이름을 받아와서 solvedAc를 등록하는 메서드
     * @param userId
     * @param username
     */
    @Override
    public void saveUserSolvedProfile(Long userId, String username) {
        saveSolvedUser.saveSolvedUser(userId, username);
        //사용자가 푼 문제들 저장
        saveUserSolvedProblem.saveUserSolvedProblem(userId);
    }
}
