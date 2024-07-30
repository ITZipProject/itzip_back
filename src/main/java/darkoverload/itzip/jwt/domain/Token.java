package darkoverload.itzip.jwt.domain;

import darkoverload.itzip.jwt.entity.TokenEntity;
import darkoverload.itzip.user.domain.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private Long id;

    private User user;

    @Setter
    private String accessToken;

    private String refreshToken;

    private String grantType;

    // 업데이트 빌더
    public Token update(String accessToken, String refreshToken, String grantType) {
        return Token.builder()
                .id(this.id)
                .user(this.user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType(grantType)
                .build();
    }

    public TokenEntity convertToEntity() {
        return TokenEntity.builder()
                .id(this.id)
                .userEntity(this.user.coverToEntity())
                .accessToken(this.accessToken)
                .refreshToken(this.refreshToken)
                .grantType(this.grantType)
                .build();
    }
}
