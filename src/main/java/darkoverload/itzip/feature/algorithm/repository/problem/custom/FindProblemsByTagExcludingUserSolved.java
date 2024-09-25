package darkoverload.itzip.feature.algorithm.repository.problem.custom;

import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FindProblemsByTagExcludingUserSolved {
    // 사용자가 해결한 문제를 제외하고 특정 태그가 있는 문제를 찾는 메세드
    // 평균 tier에 가까운 순서대로 정렬되고, 평균 tier에서 같은 거리에 있는 경우, 문제를 맞은 사용자 수가 많은 순서대로 정렬
    @Query("SELECT p FROM ProblemEntity p JOIN p.problemTagMappings ptm " +
            "WHERE ptm.problemTagEntity.bojTagId = :tagId AND p.problemId NOT IN " +
            "(SELECT upm.problemEntity.problemId FROM UserProblemMappingEntity upm WHERE upm.solvedacUserEntity.userId = :userId) " +
            "ORDER BY ABS(p.level - :averageTier) ASC, p.acceptedUserCount DESC")
    List<ProblemEntity> findProblemsByTagExcludingUserSolved(@Param("userId") Long userId, @Param("tagId") Long tagId, @Param("averageTier") int averageTier);
}