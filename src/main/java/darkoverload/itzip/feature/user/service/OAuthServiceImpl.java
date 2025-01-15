package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.techinfo.service.blog.BlogCommandService;
import darkoverload.itzip.feature.user.controller.request.GoogleUserInfo;
import darkoverload.itzip.feature.user.controller.request.GoogleUserRequest;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final BlogCommandService blogCommandService;

    @Override
    public ResponseEntity<?> google(GoogleUserRequest googleUserRequest) {
        try {
            WebClient client = WebClient.create("https://www.googleapis.com");

            // google api를 통해 google user 정보 받아오기
            GoogleUserInfo googleUserInfo = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/oauth2/v1/userinfo")
                            .queryParam("access_token", googleUserRequest.getAccessToken())
                            .build()
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(GoogleUserInfo.class)
                    .block();

            Optional<User> user = userService.findByEmail(googleUserInfo.getEmail());

            if (!user.isPresent()) { // 회원가입
                return googleSave(googleUserInfo);
            }

            if (user.get().getSnsType() == null) { // 이메일 회원이 sns 로그인 시
                throw new RestApiException(CommonExceptionCode.EMAIL_USER_SNS_LOGIN);
            }

            return userService.loginResponse(user.get()); // 로그인
        } catch (Exception e) { // 구글 api에서 오류가 발생할 경우
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }
    }

    /**
     * 구글 로그인 회원가입
     *
     * @param googleUserInfo 구글 유저 정보
     */
    private ResponseEntity<String> googleSave(GoogleUserInfo googleUserInfo) {
        User user = googleUserInfo.toUserDomain();

        user.setNickname(userService.getUniqueNickname());

        User savedUser = userRepository.save(user.convertToEntity()).convertToDomain();

        blogCommandService.create(savedUser);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}
