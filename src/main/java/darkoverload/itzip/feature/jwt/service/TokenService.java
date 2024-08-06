package darkoverload.itzip.feature.jwt.service;

import darkoverload.itzip.feature.jwt.domain.Token;
import darkoverload.itzip.feature.jwt.entity.TokenEntity;

import java.util.Optional;

public interface TokenService {
    void saveOrUpdate(Token token);

    void deleteByAccessToken(String token);

    Optional<TokenEntity> findByAccessToken(String token);

    Optional<TokenEntity> findByRefreshToken(String token);

    void updateByRefreshToken(String refreshToken, String accessToken);
}
