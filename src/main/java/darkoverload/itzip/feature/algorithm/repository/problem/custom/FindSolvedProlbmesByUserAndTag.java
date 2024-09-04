package darkoverload.itzip.feature.algorithm.repository.problem.custom;

import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FindSolvedProlbmesByUserAndTag {
    // 태그와 사용자의 id를 받아서 사용자가 푼 문제들을 찾아 반환하는 메서드
    @Query("SELECT p FROM ProblemEntity p JOIN p.userProblemMappings upm JOIN p.problemTagMappings ptm " +
            "WHERE upm.solvedacUserEntity.userId = :userId AND ptm.problemTagEntity.bojTagId = :tagId")
    List<ProblemEntity> findSolvedProblemsByUserAndTag(@Param("userId") Long userId, @Param("tagId") Long tagId);
}