package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.controller.request.*;
import darkoverload.itzip.feature.user.controller.response.UserInfoResponse;
import darkoverload.itzip.feature.user.controller.response.UserLoginResponse;
import darkoverload.itzip.feature.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface UserService {
    ResponseEntity<UserInfoResponse> getUserInfo(CustomUserDetails userDetails);

    ResponseEntity<UserLoginResponse> login(UserLoginRequest request, BindingResult bindingResult, HttpServletResponse httpServletResponse);

    String logout(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<UserLoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response);

    String save(UserJoinRequest userJoinDto, BindingResult bindingResult);

    String sendAuthEmail(AuthEmailSendRequest emailSendRequest, BindingResult bindingResult);

    String checkAuthEmail(String email, String authCode);

    String checkDuplicateEmail(String email);

    String checkDuplicateNickname(String nickname);

    String getUniqueNickname();

    Optional<User> findByEmail(String email);

    User getById(Long id);

    Optional<User> findByNickname(String nickname);

    String encryptPassword(String password);

    String tempUserOut(CustomUserDetails userDetails, HttpServletRequest request, HttpServletResponse response);
}
