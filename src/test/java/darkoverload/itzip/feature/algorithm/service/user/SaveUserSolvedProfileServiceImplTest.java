package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.algorithm.util.SaveSolvedUser;
import darkoverload.itzip.feature.algorithm.util.SaveUserSolvedProblem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveUserSolvedProfileServiceImplTest {

    @Mock
    private SaveSolvedUser saveSolvedUser;

    @Mock
    private SaveUserSolvedProblem saveUserSolvedProblem;

    @InjectMocks
    private SaveUserSolvedProfileServiceImpl saveUserSolvedProfileService;

    @Test
    void 사용자_프로필_저장() {
        Long userId = 1L;
        String username = "exampleUsername";

        saveUserSolvedProfileService.saveUserSolvedProfile(userId, username);

        verify(saveSolvedUser).saveSolvedUser(userId, username);
        verify(saveUserSolvedProblem).saveUserSolvedProblem(userId);
    }
}