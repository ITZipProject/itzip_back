package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.controller.request.ChangeNicknameRequest;
import darkoverload.itzip.feature.user.controller.request.ChangePasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface MypageService {
    String checkDuplicateNickname(String nickname);

    String changeNickname(CustomUserDetails userDetails, ChangeNicknameRequest request, BindingResult bindingResult);

    String changePassword(CustomUserDetails userDetails, ChangePasswordRequest request, BindingResult bindingResult);

    String changeImageUrl(CustomUserDetails userDetails, @RequestParam("file") MultipartFile file);
}
