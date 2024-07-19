package darkoverload.itzip.jwt.infrastructure;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 사용자 인증 및 권한 관리를 위한 사용자 세부 정보 클래스
 */
public class CustomUserDetails implements UserDetails {
    private final String email;  // 사용자 이름
    private final String password;  // 비밀번호
    private final String nickname;      // 이름
    private final List<GrantedAuthority> authorities;  // 권한 목록

    /**
     * 생성자
     *
     * @param email 이메일
     * @param password 비밀번호
     * @param nickname 닉네임
     * @param authorities 권한 목록
     */
    public CustomUserDetails(String email, String password, String nickname, List<GrantedAuthority> authorities){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
