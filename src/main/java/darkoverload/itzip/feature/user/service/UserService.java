package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.user.controller.request.UserJoinRequest;
import darkoverload.itzip.feature.user.domain.User;

import java.util.Optional;

public interface UserService {
    void save(UserJoinRequest userJoinDto);

    String getUniqueNickname();

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByNickname(String nickname);
}
