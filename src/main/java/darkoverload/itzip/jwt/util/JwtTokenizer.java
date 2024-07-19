package darkoverload.itzip.jwt.util;

import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.jwt.exception.TokenExceptionCode;
import darkoverload.itzip.user.entity.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * JWT 생성 / 검증 유틸리티 클래스
 */
@Component
public class JwtTokenizer {
    private final byte[] accessSecret;
    private final byte[] refreshSecret;
    public static Long accessTokenExpire;
    public static Long refreshTokenExpire;

    public JwtTokenizer(@Value("${jwt.accessSecret}") String accessSecret, @Value("${jwt.refreshSecret}") String refreshSecret, @Value("${jwt.accessTokenExpire}") Long accessTokenExpire, @Value("${jwt.refreshTokenExpire}") Long refreshTokenExpire) {
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
        this.accessTokenExpire = accessTokenExpire;
        this.refreshTokenExpire = refreshTokenExpire;
    }

    /**
     * AccessToken 생성
     *
     * @param id
     * @param email
     * @param nickname
     * @param authority
     * @return AccessToken
     */
    public String createAccessToken(Long id, String email, String nickname, Authority authority) {
        return createToken(id, email, nickname, authority, accessTokenExpire, accessSecret);
    }

    /**
     * RefreshToken 생성
     *
     * @param id
     * @param email
     * @param nickname
     * @param authority
     * @return RefreshToken
     */
    public String createRefreshToken(Long id, String email, String nickname, Authority authority) {
        return createToken(id, email, nickname, authority, refreshTokenExpire, refreshSecret);
    }

    /**
     * Jwts 빌더를 사용하여 token 생성
     *
     * @param id
     * @param email
     * @param nickname
     * @param authority
     * @param expire
     * @param secretKey
     * @return
     */
    private String createToken(Long id, String email, String nickname, Authority authority, Long expire, byte[] secretKey) {
        // 기본으로 가지고 있는 claim : subject
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("authority", authority);
        claims.put("userId", id);
        claims.put("nickname", nickname);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expire))
                .signWith(getSigningKey(secretKey))
                .compact();
    }

    /**
     * 토큰에서 유저 아이디 얻기
     *
     * @param token 토큰
     * @return userId
     */
    public Long getUserIdFromToken(String token) {
        String[] tokenArr = token.split(" ");
        token = tokenArr[1];
        Claims claims = parseToken(token, accessSecret);
        return Long.valueOf((Integer) claims.get("userId"));
    }

    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }

    public Claims parseToken(String token, byte[] secretKey) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) { // 토큰 유효성 체크 실패 시
            throw new RestApiException(TokenExceptionCode.JWT_INVALID_ERROR);
        }

        return claims;
    }

    /**
     * @param secretKey - byte형식
     * @return Key 형식 시크릿 키
     */
    public static Key getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }
}