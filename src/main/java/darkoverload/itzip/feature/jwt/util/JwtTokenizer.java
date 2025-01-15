package darkoverload.itzip.feature.jwt.util;

import darkoverload.itzip.feature.user.entity.Authority;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    private final byte[] tempPwSecret;
    public static Long accessTokenExpire;
    public static Long refreshTokenExpire;
    public static Long tempPwExpire;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public JwtTokenizer(@Value("${jwt.accessSecret}") String accessSecret, @Value("${jwt.refreshSecret}") String refreshSecret, @Value("${jwt.tempPwSecret}") String tempPwSecret, @Value("${jwt.accessTokenExpire}") Long accessTokenExpire, @Value("${jwt.refreshTokenExpire}") Long refreshTokenExpire, @Value("${jwt.tempPwExpire}") Long tempPwExpire) {
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
        this.tempPwSecret = tempPwSecret.getBytes(StandardCharsets.UTF_8);
        this.accessTokenExpire = accessTokenExpire;
        this.refreshTokenExpire = refreshTokenExpire;
        this.tempPwExpire = tempPwExpire;
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
        token = tokenArr[0];
        Claims claims = parseToken(token, accessSecret);
        return Long.valueOf((Integer) claims.get("userId"));
    }

    /**
     * access token 파싱
     *
     * @param accessToken access token
     * @return 파싱된 토큰
     */
    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }

    /**
     * refresh token 파싱
     *
     * @param refreshToken refresh token
     * @return 파싱된 토큰
     */
    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }

    /**
     * token 파싱
     *
     * @param token     access/refresh token
     * @param secretKey access/refresh 비밀키
     * @return 파싱된 토큰
     */
    public Claims parseToken(String token, byte[] secretKey) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException | MalformedJwtException e) {  // 토큰 유효성 체크 실패 시
            throw new RestApiException(CommonExceptionCode.JWT_INVALID_ERROR);
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

    /**
     * Request Header에서 토큰 정보를 꺼내오기 위한 resolveToken 메서드
     */
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 비밀번호 재설정 토큰 생성 메서드
     * @param email 유저 이메일
     * @param tempPassword 임시 비밀번호
     * @return 비밀번호 재설정 토큰
     */
    public String createTempPasswordToken(String email, String tempPassword) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject("TempPassword")
                .claim("email", email)
                .claim("tempPassword", tempPassword)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + tempPwExpire))
                .signWith(getSigningKey(tempPwSecret))
                .compact();
    }

    /**
     * 비밀번호 재설정 토큰 파싱 메서드
     * @param tempPwToken 비밀번호 재설정 토큰
     * @return 파싱된 토큰
     */
    public Claims parseTempPwToken(String tempPwToken) {
        return parseToken(tempPwToken, tempPwSecret);
    }
}