package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.user.controller.request.GithubUserRequest;
import darkoverload.itzip.feature.user.controller.request.GoogleUserRequest;
import org.springframework.http.ResponseEntity;

public interface OAuthService {
    ResponseEntity<?> google(GoogleUserRequest googleUserRequest);

    ResponseEntity<?> github(GithubUserRequest githubUserRequest);
}
