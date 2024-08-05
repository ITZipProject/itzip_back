package darkoverload.itzip.feature.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private String accessToken;
    private String refreshToken;

    private Long userId;
    private String nickname;
}
