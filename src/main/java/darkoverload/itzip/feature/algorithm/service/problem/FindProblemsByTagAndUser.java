package darkoverload.itzip.feature.algorithm.service.problem;

import darkoverload.itzip.feature.algorithm.controller.response.ProblemListResponse;

public interface FindProblemsByTagAndUser {
    ProblemListResponse findProblemsByTagAndUser(Long userId, Long tagId);
}
