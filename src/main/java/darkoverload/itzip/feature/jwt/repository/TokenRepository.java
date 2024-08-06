package darkoverload.itzip.feature.jwt.repository;

import darkoverload.itzip.feature.jwt.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    TokenEntity findByUserEntityId(Long userId);
    Optional<TokenEntity> findByAccessToken(String accessToken);
    Optional<TokenEntity> findByRefreshToken(String refreshToken);
}
