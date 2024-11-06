package darkoverload.itzip.feature.algorithm.service.problem;

import darkoverload.itzip.feature.algorithm.controller.response.ProblemListResponse;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

public interface FindProblemsByUserService {
    ProblemListResponse findProblemsByUser(CustomUserDetails customUserDetails);
}
