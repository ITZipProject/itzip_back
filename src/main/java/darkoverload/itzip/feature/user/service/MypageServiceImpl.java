package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.controller.request.ChangeNicknameRequest;
import darkoverload.itzip.feature.user.controller.request.ChangePasswordRequest;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@AllArgsConstructor
public class MypageServiceImpl implements MypageService {
    UserService userService;
    UserRepository userRepository;

    @Override
    public ResponseEntity<String> checkDuplicateNickname(String nickname) {
        // 닉네임을 입력하지 않은 경우
        if (nickname == null) {
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }

        // 사용 중인 닉네임일 경우
        if (userService.findByNickname(nickname).isPresent()) {
            throw new RestApiException(CommonExceptionCode.EXIST_NICKNAME_ERROR);
        }

        return ResponseEntity.ok("사용 가능한 닉네임입니다.");
    }

    @Override
    public ResponseEntity<String> changeNickname(CustomUserDetails userDetails, ChangeNicknameRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }

        // 요청 닉네임
        String nickname = request.getNickname();

        // 사용 중인 닉네임일 경우
        if (userService.findByNickname(nickname).isPresent()) {
            throw new RestApiException(CommonExceptionCode.EXIST_NICKNAME_ERROR);
        }

        // 유저 데이터를 가져와 닉네임 변경
        User user = userService.findByEmail(userDetails.getEmail()).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
        );

        user.setNickname(nickname);
        userRepository.save(user.convertToEntity());

        return ResponseEntity.ok("닉네임이 변경되었습니다.");
    }

    @Override
    public ResponseEntity<String> changePassword(CustomUserDetails userDetails, ChangePasswordRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }

        // 요청 비밀번호
        String password = request.getPassword();
        // 암호화한 비밀번호
        String encryptPassword = userService.encryptPassword(password);

        // 로그인 유저 데이터를 가져와 비밀번호 변경
        User user = userService.findByEmail(userDetails.getEmail()).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
        );

        user.setPassword(encryptPassword);
        userRepository.save(user.convertToEntity());

        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }
}
