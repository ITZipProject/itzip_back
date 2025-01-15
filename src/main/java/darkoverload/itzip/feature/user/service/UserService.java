package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.controller.request.*;
import darkoverload.itzip.feature.user.controller.response.UserInfoResponse;
import darkoverload.itzip.feature.user.controller.response.UserLoginResponse;
import darkoverload.itzip.feature.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    ResponseEntity<UserInfoResponse> getUserInfo(CustomUserDetails userDetails);

    ResponseEntity<UserLoginResponse> login(UserLoginRequest userLoginRequest);

    String logout(HttpServletRequest request);

    ResponseEntity<UserLoginResponse> refreshAccessToken(RefreshAccessTokenRequest refreshAccessTokenRequest);

    String save(UserJoinRequest userJoinRequest);

    String sendAuthEmail(AuthEmailSendRequest authEmailSendRequest);

    String checkAuthEmail(String email, String authCode);

    String checkDuplicateEmail(String email);

    String checkDuplicateNickname(String nickname);

    String getUniqueNickname();

    Optional<User> findByEmail(String email);

    User getByEmail(String email);

    User getById(Long id);

    Optional<User> findByNickname(String nickname);

    String encryptPassword(String password);

    String tempUserOut(CustomUserDetails userDetails, HttpServletRequest request);

    String requestPasswordReset(PasswordResetRequest passwordResetRequest, HttpServletRequest request);

    void confirmPasswordReset(HttpServletResponse response, String token);
}
