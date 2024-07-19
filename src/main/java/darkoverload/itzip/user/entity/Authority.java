package darkoverload.itzip.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum Authority implements GrantedAuthority {
    ADMIN,
    USER;

    /**
     * 유저의 권한을 String으로 반환하는 메소드
     * @return authority name
     */
    @Override
    public String getAuthority() {
        return name();
    }
}
