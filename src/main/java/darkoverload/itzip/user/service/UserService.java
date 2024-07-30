package darkoverload.itzip.user.service;

import darkoverload.itzip.user.controller.request.UserJoinRequest;
import darkoverload.itzip.user.entity.User;

import java.util.Optional;

public interface UserService {
    void save(UserJoinRequest userJoinDto);

    String getUniqueNickname();

    User findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByNickname(String nickname);
}
