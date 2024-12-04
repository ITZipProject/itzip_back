package darkoverload.itzip.feature.jwt.service;

import darkoverload.itzip.feature.jwt.domain.Token;
import darkoverload.itzip.feature.jwt.entity.TokenEntity;
import darkoverload.itzip.feature.jwt.repository.TokenRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    /**
     * 토큰 사용자의 토큰이 저장되어 있을 경우 updateFieldWithValue, 없을 경우 create
     *
     * @param token Token domain
     */
    @Transactional
    public void saveOrUpdate(Token token) {
        Optional<Token> oldToken = tokenRepository.findByUserEntityId(token.getUser().getId())
                .map(TokenEntity::convertToDomain);

        if (oldToken.isEmpty()) {
            tokenRepository.save(token.convertToEntity());
        } else {
            oldToken.get().update(token.getAccessToken(), token.getRefreshToken(), token.getGrantType());

            tokenRepository.save(oldToken.get().convertToEntity());
        }
    }

    /**
     * access token으로 Token 데이터를 가져와 삭제하는 메소드
     *
     * @param token
     */
    @Transactional
    public void deleteByAccessToken(String token) {
        tokenRepository.findByAccessToken(token).ifPresent(tokenRepository::delete);
    }

    /**
     * access token으로 Token 데이터를 가져오는 메소드
     *
     * @param token access token
     * @return Token 데이터
     */
    @Transactional(readOnly = true)
    public Optional<TokenEntity> findByAccessToken(String token) {
        return tokenRepository.findByAccessToken(token);
    }

    /**
     * refresh token으로 Token 데이터를 가져오는 메소드
     *
     * @param token refresh token
     * @return Token 데이터
     */
    @Transactional(readOnly = true)
    public Optional<TokenEntity> findByRefreshToken(String token) {
        return tokenRepository.findByRefreshToken(token);
    }

    /**
     * refresh token으로 Token 데이터를 가져와 access token 값을 업데이트하는 메소드
     *
     * @param refreshToken
     * @param accessToken
     */
    @Transactional
    public void updateByRefreshToken(String refreshToken, String accessToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.JWT_UNKNOWN_ERROR)).convertToDomain();

        token.setAccessToken(accessToken);
        tokenRepository.save(token.convertToEntity());
    }
}
