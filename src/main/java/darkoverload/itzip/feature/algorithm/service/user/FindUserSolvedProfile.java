package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.algorithm.controller.response.SolvedUserResponse;

public interface FindUserSolvedProfile {
    SolvedUserResponse findUserSolvedProfile(Long userId);
}
