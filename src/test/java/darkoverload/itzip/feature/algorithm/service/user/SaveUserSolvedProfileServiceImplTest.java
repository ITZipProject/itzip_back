package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.algorithm.util.SaveSolvedUser;
import darkoverload.itzip.feature.algorithm.util.SaveUserSolvedProblem;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveUserSolvedProfileServiceImplTest {

    @Mock
    private SaveSolvedUser saveSolvedUser;

    @Mock
    private SaveUserSolvedProblem saveUserSolvedProblem;

    @Mock
    private UserService userService;

    @InjectMocks
    private SaveUserSolvedProfileServiceImpl saveUserSolvedProfileService;

    @Test
    void 사용자_프로필_저장() {
        // 이메일과 사용자 ID 설정
        String email = "test@example.com";
        Long userId = 1L;
        String username = "exampleUsername";

        // CustomUserDetails 설정
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getEmail()).thenReturn(email);

        // User 객체 모킹
        User user = mock(User.class);
        when(user.getId()).thenReturn(userId);
        when(userService.findByEmail(email)).thenReturn(Optional.of(user));

        // 서비스 호출
        saveUserSolvedProfileService.saveUserSolvedProfile(customUserDetails, username);

        // 검증
        verify(saveSolvedUser).saveSolvedUser(userId, username);  // userId를 기반으로 사용자 정보 저장
        verify(saveUserSolvedProblem).saveUserSolvedProblem(userId);  // userId를 기반으로 문제 저장
    }
}