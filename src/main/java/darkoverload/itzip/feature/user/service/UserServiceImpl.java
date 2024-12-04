package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.jwt.domain.Token;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.jwt.service.TokenService;
import darkoverload.itzip.feature.jwt.util.JwtTokenizer;
import darkoverload.itzip.feature.techinfo.service.blog.BlogCommandService;
import darkoverload.itzip.feature.user.controller.request.AuthEmailSendRequest;
import darkoverload.itzip.feature.user.controller.request.RefreshAccessTokenRequest;
import darkoverload.itzip.feature.user.controller.request.UserJoinRequest;
import darkoverload.itzip.feature.user.controller.request.UserLoginRequest;
import darkoverload.itzip.feature.user.controller.response.UserInfoResponse;
import darkoverload.itzip.feature.user.controller.response.UserLoginResponse;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.Authority;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.feature.user.util.RandomAuthCode;
import darkoverload.itzip.feature.user.util.RandomNickname;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RandomNickname randomNickname;
    private final VerificationService verificationService;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final BlogCommandService blogCommandService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserEntity::convertToDomain);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).map(UserEntity::convertToDomain).orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));
    }

    /**
     * 로그인 유저 정보 반환
     */
    @Override
    public ResponseEntity<UserInfoResponse> getUserInfo(CustomUserDetails userDetails) {
        // 로그인한 유저의 이메일과 일치하는 유저 데이터 가져오기
        User user = findByEmail(userDetails.getEmail()).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
        );

        // 이메일, 닉네임, 프로필 이미지 url 반환
        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .build();

        return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
    }

    /**
     * 로그인
     */
    @Override
    @Transactional
    public ResponseEntity<UserLoginResponse> login(UserLoginRequest userLoginRequest) {
        User user = findByEmail(userLoginRequest.getEmail()).orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_MATCH_PASSWORD));

        // 비밀번호 일치여부 체크
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new RestApiException(CommonExceptionCode.NOT_MATCH_PASSWORD);
        }

        // 토큰 발급
        String accessToken = jwtTokenizer.createAccessToken(user.getId(), user.getEmail(), user.getNickname(), user.getAuthority());
        String refreshToken = jwtTokenizer.createRefreshToken(user.getId(), user.getEmail(), user.getNickname(), user.getAuthority());

        // 리프레시 토큰 디비 저장
        Token token = Token.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType("Bearer")
                .build();

        tokenService.saveOrUpdate(token);

        // 응답 값
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();

        return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
    }

    /**
     * 로그아웃
     */
    @Override
    public String logout(HttpServletRequest request) {
        // access token 가져오기
        String accessToken = jwtTokenizer.resolveAccessToken(request);

        // tokens 데이터 삭제
        tokenService.deleteByAccessToken(accessToken);
        return "로그아웃 되었습니다.";
    }

    /**
     * 리프레시 토큰으로 엑세스 토큰 갱신
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UserLoginResponse> refreshAccessToken(RefreshAccessTokenRequest refreshAccessTokenRequest) {
        String refreshToken = refreshAccessTokenRequest.getRefreshToken();

        // refresh token 파싱
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);

        // 유저 정보 가져오기
        Long userId = Long.valueOf((Integer) claims.get("userId"));
        User user = getById(userId);
        Authority authority = Authority.valueOf(claims.get("authority", String.class));

        // access token 생성
        String accessToken = jwtTokenizer.createAccessToken(userId, user.getEmail(), user.getNickname(), authority);

        // Token 데이터 access token 값 업데이트
        tokenService.updateByRefreshToken(refreshToken, accessToken);

        // 응답 값
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(user.getNickname())
                .userId(user.getId())
                .build();

        return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
    }

    /**
     * 회원가입
     */
    @Transactional
    public String save(UserJoinRequest userJoinRequest) {
        // 이메일 중복 체크
        if (findByEmail(userJoinRequest.getEmail()).isPresent()) {
            throw new RestApiException(CommonExceptionCode.EXIST_EMAIL_ERROR);
        }

        // 이메일 인증번호 체크
        if (!verificationService.verifyCode(userJoinRequest.getEmail(), userJoinRequest.getAuthCode())) {
            throw new RestApiException(CommonExceptionCode.NOT_MATCH_AUTH_CODE);
        }

        // 저장된 인증번호 삭제
        verificationService.deleteCode(userJoinRequest.getEmail());

        User user = userJoinRequest.toDomain();

        // 비밀번호 암호화
        String encryptedPassword = encryptPassword(userJoinRequest.getPassword());

        user.setPassword(encryptedPassword);
        user.setNickname(getUniqueNickname());

        userRepository.save(user.convertToEntity());

        blogCommandService.create(user);

        return "회원가입이 완료되었습니다.";
    }


    @Transactional(readOnly = true)
    public String sendAuthEmail(AuthEmailSendRequest authEmailSendRequest) {
        // 랜덤 인증 코드 생성
        String authCode = RandomAuthCode.generate();

        // redis에 인증 코드 저장
        verificationService.saveCode(authEmailSendRequest.getEmail(), authCode);

        // 메일 제목
        String subject = "[ITZIP] 이메일 인증번호 : " + authCode;

        // 메일 본문
        String body = emailService.setAuthForm(authCode);

        // 메일 발송
        emailService.sendFormMail(authEmailSendRequest.getEmail(), subject, body);
        return "인증 메일이 발송되었습니다.";
    }

    /**
     * 인증번호 검증
     */
    @Override
    public String checkAuthEmail(String email, String authCode) {
        // redis에 저장된 인증번호와 비교하여 확인
        if (!verificationService.verifyCode(email, authCode)) {
            throw new RestApiException(CommonExceptionCode.NOT_MATCH_AUTH_CODE);
        }

        return "인증이 완료되었습니다.";
    }

    /**
     * 사용 중인 이메일인지 체크
     */
    @Override
    public String checkDuplicateEmail(String email) {
        // 중복 이메일 체크
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new RestApiException(CommonExceptionCode.EXIST_EMAIL_ERROR);
        });
        return "사용 가능한 이메일입니다.";
    }

    @Override
    public String checkDuplicateNickname(String nickname) {
        // 닉네임을 입력하지 않은 경우
        if (nickname == null) {
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }

        // 사용 중인 닉네임일 경우
        if (findByNickname(nickname).isPresent()) {
            throw new RestApiException(CommonExceptionCode.EXIST_NICKNAME_ERROR);
        }
        return "사용 가능한 닉네임입니다.";
    }

    /**
     * 임시 회원 탈퇴
     */
    @Override
    public String tempUserOut(CustomUserDetails userDetails, HttpServletRequest request) {
        logout(request);

        User user = findByEmail(userDetails.getEmail()).orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));

        userRepository.delete(user.convertToEntity());

        return "정상적으로 탈퇴 되었습니다.";
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

    /**
     * userId로 user 데이터를 가져오는 메소드
     *
     * @param id user id
     * @return User 도메인
     */
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
        ).convertToDomain();
    }

    /**
     * 닉네임으로 user 데이터를 가져오는 메소드
     *
     * @param nickname user nickname
     * @return Optional User 도메인
     */
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
    public String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
