package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.user.controller.request.EmailSendRequest;
import darkoverload.itzip.feature.user.controller.request.UserJoinRequest;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.feature.user.util.RandomAuthCode;
import darkoverload.itzip.feature.user.util.RandomNickname;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RandomNickname randomNickname;
    private final VerificationService verificationService;
    private final EmailService emailService;

    @Transactional
    public void save(UserJoinRequest userJoinDto) {
        // 이메일 중복 체크
        if (findByEmail(userJoinDto.getEmail()).isPresent()) {
            throw new RestApiException(CommonExceptionCode.EXIST_EMAIL_ERROR);
        }

        // 이메일 인증번호 체크
        if (!verificationService.verifyCode(userJoinDto.getEmail(), userJoinDto.getAuthCode())) {
            throw new RestApiException(CommonExceptionCode.NOT_MATCH_AUTH_CODE);
        }

        User user = userJoinDto.toDomain();

        // 비밀번호 암호화
        String encryptedPassword = encryptPassword(userJoinDto.getPassword());

        user.setPassword(encryptedPassword);
        user.setNickname(getUniqueNickname());

        userRepository.save(user.convertToEntity());
    }

    /**
     * 중복되지 않은 랜덤 닉네임 생성
     *
     * @return unique random nickname
     */
    public String getUniqueNickname() {
        String nickname;
        do {
            nickname = randomNickname.generate();
        } while (findByNickname(nickname).isPresent());

        return nickname;
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserEntity::convertToDomain);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(UserEntity::convertToDomain);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).map(UserEntity::convertToDomain);
    }

    /**
     * 비밀번호 암호화
     *
     * @param password 비밀번호
     * @return 암호화된 비밀번호
     */
    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<String> sendAuthEmail(EmailSendRequest emailSendRequest, BindingResult bindingResult) {
        // 필드 에러 확인
        if (bindingResult.hasErrors()) {
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }

        // 랜덤 인증 코드 생성
        String authCode = RandomAuthCode.generate();

        // redis에 인증 코드 저장
        verificationService.saveCode(emailSendRequest.getEmail(), authCode);

        // 메일 제목
        String subject = "[ITZIP] 이메일 인증번호 : " + authCode;

        // 메일 본문
        String body = emailService.setAuthForm(authCode);

        // 메일 발송
        emailService.sendFormMail(emailSendRequest.getEmail(), subject, body);
        return ResponseEntity.ok("인증 메일이 발송되었습니다.");
    }

}
