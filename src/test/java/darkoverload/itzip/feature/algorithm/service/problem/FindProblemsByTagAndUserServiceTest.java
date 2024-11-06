package darkoverload.itzip.feature.algorithm.service.problem;

import darkoverload.itzip.feature.algorithm.controller.response.ProblemListResponse;
import darkoverload.itzip.feature.algorithm.domain.SolvedacUser;
import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.repository.problem.ProblemRepository;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.feature.algorithm.util.SolvedTierCalculator;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
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
import static org.mockito.ArgumentMatchers.eq;
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
        Long userId = 1L;  // userId는 1L로 설정
        Long tagId = 2L;
        String email = "test@example.com";

        SolvedacUserEntity solvedacUserEntity = mock(SolvedacUserEntity.class);
        SolvedacUser solvedacUser = mock(SolvedacUser.class);

        // 이메일로 SolvedacUserEntity를 찾는 로직 추가
        when(solvedacUserRepository.findByUserEntityEmail(email)).thenReturn(Optional.of(solvedacUserEntity));
        when(solvedacUserEntity.convertToDomain()).thenReturn(solvedacUser);
        when(solvedacUser.getTier()).thenReturn(10);

        // Tier 계산 관련 모킹
        when(solvedTierCalculator.tagRatingCalculator(any())).thenReturn(8);
        when(solvedTierCalculator.tierCalculator(8)).thenReturn(8);

        // 사용자 문제 리스트 조회 모킹 - userId로 전달되는 값을 any()로 변경하여 인자 값 무시
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getEmail()).thenReturn(email);  // 이메일 설정
        List<ProblemEntity> problemEntities = List.of(mock(ProblemEntity.class));
        when(problemRepository.findProblemsByTagExcludingUserSolved(any(Long.class), eq(tagId), eq(9), any(PageRequest.class)))
                .thenReturn(problemEntities);

        // 서비스 호출
        ProblemListResponse response = findProblemsByTagAndUserService.findProblemsByTagAndUser(customUserDetails, tagId);

        // Assertions
        assertNotNull(response);
        assertEquals(problemEntities.size(), response.getProblems().size());
    }
}