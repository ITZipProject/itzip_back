package darkoverload.itzip.feature.algorithm.service.problem;

import darkoverload.itzip.feature.algorithm.controller.response.ProblemListResponse;
import darkoverload.itzip.feature.algorithm.domain.SolvedacUser;
import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.repository.problem.ProblemRepository;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.feature.algorithm.util.SolvedTierCalculator;
import lombok.extern.slf4j.Slf4j;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class FindProblemsByTagAndUserServiceImplTest {

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private SolvedacUserRepository solvedacUserRepository;

    @Mock
    private SolvedTierCalculator solvedTierCalculator;

    @InjectMocks
    private FindProblemsByTagAndUserServiceImpl findProblemsByTagAndUserService;

    @Test
    void 사용자_아이디와_테그_아이디로_문제를_추천() {
        Long userId = 1L;
        Long tagId = 2L;

        SolvedacUserEntity solvedacUserEntity = mock(SolvedacUserEntity.class);
        SolvedacUser solvedacUser = mock(SolvedacUser.class);

        when(solvedacUserRepository.findById(userId)).thenReturn(Optional.of(solvedacUserEntity));
        when(solvedacUserEntity.convertToDomain()).thenReturn(solvedacUser);
        when(solvedacUser.getTier()).thenReturn(10);

        when(solvedTierCalculator.tagRatingCalculator(any())).thenReturn(8);
        when(solvedTierCalculator.tierCalculator(8)).thenReturn(8);

        List<ProblemEntity> problemEntities = List.of(mock(ProblemEntity.class));
        when(problemRepository.findProblemsByTagExcludingUserSolved(userId, tagId, 9, PageRequest.of(0, 10)))
                .thenReturn(problemEntities);

        ProblemListResponse response = findProblemsByTagAndUserService.findProblemsByTagAndUser(userId, tagId);

        assertNotNull(response);
        assertEquals(problemEntities.size(), response.getProblems().size());
    }
}