package darkoverload.itzip.feature.algorithm.repository.problem;

import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import darkoverload.itzip.feature.algorithm.repository.problem.custom.FindProblemByUser;
import darkoverload.itzip.feature.algorithm.repository.problem.custom.FindProblemsByTagExcludingUserSolved;
import darkoverload.itzip.feature.algorithm.repository.problem.custom.FindSolvedProlbmesByUserAndTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Long>,
        FindProblemByUser,
        FindProblemsByTagExcludingUserSolved,
        FindSolvedProlbmesByUserAndTag
{}