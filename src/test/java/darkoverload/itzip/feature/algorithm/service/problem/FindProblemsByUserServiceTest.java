package darkoverload.itzip.feature.algorithm.service.problem;

import darkoverload.itzip.feature.algorithm.controller.response.ProblemListResponse;
import darkoverload.itzip.feature.algorithm.domain.SolvedacUser;
import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.repository.problem.ProblemRepository;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindProblemsByUserServiceImplTest {

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private SolvedacUserRepository solvedacUserRepository;

    @InjectMocks
    private FindProblemsByUserServiceImpl findProblemsByUserService;

    @Test
    void 사용자의_tier_기준으로_문제를_추천() {
        String email = "test@example.com";

        // CustomUserDetails 설정
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getEmail()).thenReturn(email);

        // SolvedacUserEntity 설정
        SolvedacUserEntity solvedacUserEntity = mock(SolvedacUserEntity.class);
        when(solvedacUserRepository.findByUserEntityEmail(email)).thenReturn(Optional.of(solvedacUserEntity));

        SolvedacUser solvedacUser = mock(SolvedacUser.class);
        when(solvedacUserEntity.convertToDomain()).thenReturn(solvedacUser);
        when(solvedacUser.getTier()).thenReturn(5);

        // 문제 리스트 설정
        List<ProblemEntity> problemEntities = List.of(mock(ProblemEntity.class));
        when(problemRepository.findProblemsByUser(solvedacUserEntity.getUserId(), 5, PageRequest.of(0, 10)))
                .thenReturn(problemEntities);

        // 서비스 호출
        ProblemListResponse response = findProblemsByUserService.findProblemsByUser(customUserDetails);

        // Assertions
        assertNotNull(response);
        assertEquals(problemEntities.size(), response.getProblems().size());
    }
}