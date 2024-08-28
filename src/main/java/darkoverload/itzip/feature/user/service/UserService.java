package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.user.controller.request.EmailCheckRequest;
import darkoverload.itzip.feature.user.controller.request.EmailSendRequest;
import darkoverload.itzip.feature.user.controller.request.UserJoinRequest;
import darkoverload.itzip.feature.user.controller.request.UserLoginRequest;
import darkoverload.itzip.feature.user.controller.response.UserLoginResponse;
import darkoverload.itzip.feature.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface UserService {
    ResponseEntity<UserLoginResponse> login(UserLoginRequest request, BindingResult bindingResult, HttpServletResponse httpServletResponse);

    ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<UserLoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<String> save(UserJoinRequest userJoinDto, BindingResult bindingResult);

    ResponseEntity<String> sendAuthEmail(EmailSendRequest emailSendRequest, BindingResult bindingResult);

    ResponseEntity<String> checkAuthEmail(EmailCheckRequest request, BindingResult bindingResult);

    String getUniqueNickname();

    Optional<User> findByEmail(String email);

    User getById(Long id);

    Optional<User> findByNickname(String nickname);
}
