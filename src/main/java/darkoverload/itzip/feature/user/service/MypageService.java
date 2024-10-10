package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.controller.request.ChangeNicknameRequest;
import darkoverload.itzip.feature.user.controller.request.ChangePasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface MypageService {
    ResponseEntity<String> checkDuplicateNickname(String nickname);

    ResponseEntity<String> changeNickname(CustomUserDetails userDetails, ChangeNicknameRequest changeNicknameRequest);

    ResponseEntity<String> changePassword(CustomUserDetails userDetails, ChangePasswordRequest changePasswordRequest);

    ResponseEntity<String> changeImageUrl(CustomUserDetails userDetails, @RequestParam("file") MultipartFile file);
}
