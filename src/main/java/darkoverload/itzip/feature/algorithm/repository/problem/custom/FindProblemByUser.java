package darkoverload.itzip.feature.algorithm.repository.problem.custom;

import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FindProblemByUser {
    // 사용자가 해결한 문제를 제외하고 사용자의 tier랑 비슷한 문제를 찾는 메서드
    // 사용자 티어에 2정도 차이나는 곳으로 문제를 묶고 문제를 맞은 사용자 수가 많은 순서대로 정렬
    @Query("SELECT p FROM ProblemEntity p WHERE p.level BETWEEN :tier - 2 AND :tier + 2 " +
            "AND p.problemId NOT IN (SELECT upm.problemEntity.problemId FROM UserProblemMappingEntity upm WHERE upm.solvedacUserEntity.userId = :userId) " +
            "ORDER BY p.acceptedUserCount DESC")
    List<ProblemEntity> findProblemsByUser(@Param("userId") Long userId, @Param("tier") int tier);
}