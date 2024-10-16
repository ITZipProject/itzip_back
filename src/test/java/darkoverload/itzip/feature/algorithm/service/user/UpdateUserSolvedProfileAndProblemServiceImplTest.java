package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.algorithm.controller.response.SolvedUserResponse;
import darkoverload.itzip.feature.algorithm.domain.SolvedacUser;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.feature.algorithm.util.SaveSolvedUser;
import darkoverload.itzip.feature.algorithm.util.SaveUserSolvedProblem;
import darkoverload.itzip.feature.image.service.CloudStorageService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserSolvedProfileAndProblemServiceImplTest {

    @Mock
    private SaveSolvedUser saveSolvedUser;

    @Mock
    private SaveUserSolvedProblem saveUserSolvedProblem;

    @Mock
    private SolvedacUserRepository solvedacUserRepository;

    @Mock
    private CloudStorageService cloudStorageService;

    @InjectMocks
    private UpdateUserSolvedProfileAndProblemServiceImpl updateUserSolvedProfileAndProblemService;

    @Test
    void 사용자가_업데이트_쿨다운을_지켰을_경우_프로필_업데이트() {
        Long userId = 1L;
        SolvedacUserEntity solvedacUserEntity = mock(SolvedacUserEntity.class);
        when(solvedacUserRepository.findById(userId)).thenReturn(Optional.of(solvedacUserEntity));

        SolvedacUser solvedacUser = mock(SolvedacUser.class);
        when(solvedacUserEntity.convertToDomain()).thenReturn(solvedacUser);
        when(solvedacUser.getUpdateTime()).thenReturn(LocalDateTime.now().minusHours(2));

        when(saveSolvedUser.saveSolvedUser(userId, solvedacUser.getUsername())).thenReturn(solvedacUser);

        SolvedUserResponse response = updateUserSolvedProfileAndProblemService.updateUserSolvedProfileAndProblem(userId);

        assertNotNull(response);
        assertEquals(solvedacUser, response.getSolvedacUser());
        verify(saveUserSolvedProblem).saveUserSolvedProblem(userId);
    }

    @Test
    void 사용자가_업데이트_쿨다운_미준수시_예외_발생() {
        Long userId = 1L;
        SolvedacUserEntity solvedacUserEntity = mock(SolvedacUserEntity.class);
        when(solvedacUserRepository.findById(userId)).thenReturn(Optional.of(solvedacUserEntity));

        SolvedacUser solvedacUser = mock(SolvedacUser.class);
        when(solvedacUserEntity.convertToDomain()).thenReturn(solvedacUser);
        when(solvedacUser.getUpdateTime()).thenReturn(LocalDateTime.now());

        RestApiException exception = assertThrows(RestApiException.class, () ->
                updateUserSolvedProfileAndProblemService.updateUserSolvedProfileAndProblem(userId)
        );
        assertEquals(CommonExceptionCode.UPDATE_COOLDOWN, exception.getExceptionCode());
    }
}