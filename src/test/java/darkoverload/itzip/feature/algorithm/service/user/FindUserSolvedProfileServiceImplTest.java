package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.algorithm.controller.response.SolvedUserResponse;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserSolvedProfileServiceImplTest {

    @Mock
    private SolvedacUserRepository solvedacUserRepository;

    @InjectMocks
    private FindUserSolvedProfileServiceImpl findUserSolvedProfileService;

    @Test
    void 사용자_프로필_정상_반환() {
        Long userId = 1L;
        SolvedacUserEntity solvedacUserEntity = mock(SolvedacUserEntity.class);
        when(solvedacUserRepository.findById(userId)).thenReturn(Optional.of(solvedacUserEntity));

        SolvedUserResponse response = findUserSolvedProfileService.findUserSolvedProfile(userId);

        assertEquals(solvedacUserEntity.convertToDomain(), response.getSolvedacUser());
    }

    @Test
    void 사용자_프로필_없을_경우_예외() {
        Long userId = 2L;
        when(solvedacUserRepository.findById(userId)).thenReturn(Optional.empty());

        RestApiException exception = assertThrows(RestApiException.class, () ->
                findUserSolvedProfileService.findUserSolvedProfile(userId)
        );
        assertEquals(CommonExceptionCode.NOT_FOUND_SOLVEDAC_USER, exception.getExceptionCode());
    }
}