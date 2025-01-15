package darkoverload.itzip.feature.user.controller;

import darkoverload.itzip.feature.user.controller.request.GoogleUserRequest;
import darkoverload.itzip.feature.user.service.OAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "OAuth", description = "SNS 로그인 기능 API")
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;

    @Operation(
            summary = "구글 로그인",
            description = "구글 엑세스 토큰 입력받아 로그인에 필요한 itzip 엑세스 토큰을 발급하거나 회원가입 합니다."
    )
    @PostMapping("/google")
    public ResponseEntity<?> google(@RequestBody @Valid GoogleUserRequest googleUserRequest) {
        return oAuthService.google(googleUserRequest);
    }
}
