package darkoverload.itzip.jwt.infrastructure;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * JWT 인증 토큰 클래스
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String token;      // JWT 토큰
    private Object principal;  // 주체 (사용자)
    private Object credentials; // 인증 정보 (자격 증명)

    /**
     * 인증된 토큰을 생성하는 생성자
     *
     * @param authorities 권한
     * @param principal   주체 (사용자)
     * @param credentials 인증 정보 (자격 증명)
     */
    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentials) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(true); // 토큰을 인증된 상태로 설정
    }

    /**
     * 인증되지 않은 토큰을 생성하는 생성자
     *
     * @param token JWT 토큰
     */
    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
        this.setAuthenticated(false); // 토큰을 인증되지 않은 상태로 설정
    }

    /**
     * 자격 증명을 반환
     *
     * @return 자격 증명
     */
    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    /**
     * 주체 (사용자)를 반환
     *
     * @return 주체 (사용자)
     */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
