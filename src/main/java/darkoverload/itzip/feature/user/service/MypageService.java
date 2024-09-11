package darkoverload.itzip.feature.user.service;

import org.springframework.http.ResponseEntity;

public interface MypageService {
    ResponseEntity<String> checkDuplicateNickname(String nickname);
}
