package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.algorithm.controller.response.SolvedUserResponse;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

public interface FindUserSolvedProfileService {
    SolvedUserResponse findUserSolvedProfile(CustomUserDetails customUserDetails);
}
