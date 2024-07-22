package darkoverload.itzip.user.service;

import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.user.dto.UserJoinDto;
import darkoverload.itzip.user.entity.User;
import darkoverload.itzip.user.exception.UserExceptionCode;
import darkoverload.itzip.user.repository.UserRepository;
import darkoverload.itzip.user.util.RandomNickname;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RandomNickname randomNickname;

    @Transactional
    public void save(UserJoinDto userJoinDto) {
        // 이메일 중복 체크
        if (findByEmail(userJoinDto.getEmail()) != null) {
            throw new RestApiException(UserExceptionCode.EXIST_EMAIL_ERROR);
        }

        User user = userJoinDto.toEntity();

        // 비밀번호 암호화
        String encryptedPassword = encryptPassword(userJoinDto.getPassword());

        user.setPassword(encryptedPassword);
        user.setNickname(getUniqueNickname());

        userRepository.save(user);
    }

    private String getUniqueNickname() {
        String nickname;
        do {
            nickname = randomNickname.generate();
        } while (findByNickname(nickname).isPresent());

        return nickname;
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
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
}
