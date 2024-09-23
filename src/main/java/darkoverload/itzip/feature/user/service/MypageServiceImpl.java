package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MypageServiceImpl implements MypageService {
    UserService userService;

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
}
