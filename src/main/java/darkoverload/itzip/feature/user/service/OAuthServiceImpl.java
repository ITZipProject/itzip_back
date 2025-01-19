package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.techinfo.service.blog.BlogCommandService;
import darkoverload.itzip.feature.user.controller.request.GithubUserInfo;
import darkoverload.itzip.feature.user.controller.request.GithubUserRequest;
import darkoverload.itzip.feature.user.controller.request.GoogleUserInfo;
import darkoverload.itzip.feature.user.controller.request.GoogleUserRequest;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
        // 구글 유저 정보 조회
        GoogleUserInfo googleUserInfo = fetchGoogleUserInfo(googleUserRequest);

        // 로그인/회원가입 처리
        return handleSnsLogin(
                googleUserInfo.toUserDomain()
        );
    }

    @Override
    public ResponseEntity<?> github(GithubUserRequest githubUserRequest) {
        // 깃허브 유저 정보 조회
        GithubUserInfo githubUserInfo = fetchGithubUserInfo(githubUserRequest);

        // 로그인/회원가입 처리
        return handleSnsLogin(
                githubUserInfo.toUserDomain()
        );
    }

    /**
     * SNS 로그인/회원가입 공통 처리 로직
     *
     * @param userDomain user 도메인
     * @return ResponseEntity
     */
    private ResponseEntity<?> handleSnsLogin(User userDomain) {
        Optional<User> userOptional = userService.findByEmail(userDomain.getEmail());

        // 회원가입
        if (!userOptional.isPresent()) {
            return save(userDomain);
        }

        User user = userOptional.get();

        if (user.getSnsType() == null) { // 기존 이메일 회원이 SNS로 로그인 시도할 경우 예외
            throw new RestApiException(CommonExceptionCode.EMAIL_USER_SNS_LOGIN);
        }

        if (!user.getSnsType().equals(userDomain.getSnsType())) {
            switch (user.getSnsType()) {
                case "google":
                    throw new RestApiException(CommonExceptionCode.GOOGLE_USER_GITHUB_LOGIN);
                case "github":
                    throw new RestApiException(CommonExceptionCode.GITHUB_USER_GOOGLE_LOGIN);
            }
        }

        // SNS 회원이라면 로그인 처리
        return userService.loginResponse(user);
    }

    /**
     * 구글 유저 정보 가져오기
     */
    private GoogleUserInfo fetchGoogleUserInfo(GoogleUserRequest googleUserRequest) {
        try {
            WebClient client = WebClient.create("https://www.googleapis.com");
            return client
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
        } catch (Exception e) {
            // 구글 API 호출 오류
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }
    }

    /**
     * 깃허브 유저 정보 가져오기
     */
    private GithubUserInfo fetchGithubUserInfo(GithubUserRequest githubUserRequest) {
        try {
            WebClient client = WebClient.create("https://api.github.com");
            return client
                    .get()
                    .uri("/user")
                    .header(HttpHeaders.AUTHORIZATION, "token " + githubUserRequest.getAccessToken())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(GithubUserInfo.class)
                    .block();
        } catch (Exception e) {
            // 깃허브 API 호출 오류
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }
    }

    /**
     * SNS 로그인 회원가입
     *
     * @param user 회원가입할 유저
     */
    private ResponseEntity<String> save(User user) {
        user.setNickname(userService.getUniqueNickname());     // 닉네임 중복 방지 로직
        User savedUser = userRepository.save(user.convertToEntity()).convertToDomain();
        blogCommandService.create(savedUser);                  // 블로그 생성 로직
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}
