package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.user.controller.request.EmailSendRequest;
import darkoverload.itzip.feature.user.controller.request.UserJoinRequest;
import darkoverload.itzip.feature.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface UserService {
    void save(UserJoinRequest userJoinDto);

    String getUniqueNickname();

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByNickname(String nickname);

    ResponseEntity<String> sendAuthEmail(EmailSendRequest emailSendRequest, BindingResult bindingResult);
}
