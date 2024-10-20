package darkoverload.itzip.feature.algorithm.service.user;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

public interface SaveUserSolvedProfileService {
    void saveUserSolvedProfile(CustomUserDetails customUserDetails, String username);
}