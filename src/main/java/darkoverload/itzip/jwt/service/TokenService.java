package darkoverload.itzip.jwt.service;

import darkoverload.itzip.jwt.entity.Token;
import darkoverload.itzip.jwt.exception.CustomJwtException;
import darkoverload.itzip.jwt.exception.TokenExceptionCode;
import darkoverload.itzip.jwt.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    /**
     * 토큰 사용자의 토큰이 저장되어 있을 경우 update, 없을 경우 create
     * @param token Token 데이터
     */
    @Transactional
    public void saveOrUpdate(Token token) {
        Token oldToken = tokenRepository.findByUserId(token.getUser().getId());
        if (oldToken != null) {
            oldToken.update(token.getAccessToken(), token.getRefreshToken(), token.getGrantType());

            tokenRepository.save(oldToken);
        } else {
            tokenRepository.save(token);
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
    public Optional<Token> findByAccessToken(String token) {
        return tokenRepository.findByAccessToken(token);
    }

    /**
     * refresh token으로 Token 데이터를 가져오는 메소드
     *
     * @param token refresh token
     * @return Token 데이터
     */
    @Transactional(readOnly = true)
    public Optional<Token> findByRefreshToken(String token) {
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
                .orElseThrow(() -> new CustomJwtException(TokenExceptionCode.JWT_UNKNOWN_ERROR));

        token.setAccessToken(accessToken);
        tokenRepository.save(token);
    }
}
