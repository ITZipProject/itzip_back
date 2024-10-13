package darkoverload.itzip.feature.jwt.filter;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.jwt.infrastructure.JwtAuthenticationToken;
import darkoverload.itzip.feature.jwt.util.JwtTokenizer;
import darkoverload.itzip.feature.user.entity.Authority;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * JWT 인증 필터 클래스
 */
@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 필터 메서드
     * 각 요청마다 JWT 토큰을 검증하고 인증을 설정
     *
     * @param request     요청 객체
     * @param response    응답 객체
     * @param filterChain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException      입출력 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 쿠키에 저장된 accessToken 값을 가져옴
        String accessToken = resolveToken(request);

        if (StringUtils.hasText(accessToken)) {
            try {
                getAuthentication(accessToken); // 토큰을 사용하여 인증 설정
            } catch (ExpiredJwtException e) {
                throw new RestApiException(CommonExceptionCode.JWT_UNKNOWN_ERROR);
            } catch (UnsupportedJwtException e) {
                throw new RestApiException(CommonExceptionCode.JWT_UNSUPPORTED_ERROR);
            } catch (MalformedJwtException e) {
                throw new RestApiException(CommonExceptionCode.JWT_INVALID_ERROR);
            } catch (IllegalArgumentException e) {
                throw new RestApiException(CommonExceptionCode.JWT_UNKNOWN_ERROR);
            } catch (Exception e) {
                throw new RestApiException(CommonExceptionCode.JWT_INTERNAL_ERROR);
            }
        }
        filterChain.doFilter(request, response); // 다음 필터로 요청을 전달
    }

    /**
     * 토큰을 사용하여 인증 설정
     *
     * @param token JWT 토큰
     */
    private void getAuthentication(String token) {
        Claims claims = jwtTokenizer.parseAccessToken(token); // 토큰에서 클레임을 파싱
        String email = claims.getSubject(); // 이메일을 가져옴
        String nickname = claims.get("nickname", String.class); // 이름을 가져옴
        Authority authority = Authority.valueOf(claims.get("authority", String.class)); // 사용자 권한을 가져옴

        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(authority);

        CustomUserDetails userDetails = new CustomUserDetails(email, "", nickname, (List<GrantedAuthority>) authorities);
        Authentication authentication = new JwtAuthenticationToken(authorities, userDetails, null); // 인증 객체 생성
        SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContextHolder에 인증 객체 설정
    }

    /**
     * Request Header에서 토큰 정보를 꺼내오기 위한 resolveToken 메서드
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
