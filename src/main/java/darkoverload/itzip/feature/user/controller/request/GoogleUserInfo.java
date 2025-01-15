package darkoverload.itzip.feature.user.controller.request;

import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.Authority;
import lombok.Getter;
import lombok.Setter;

/**
 * 구글 api 유저 정보 dto
 */
@Getter
@Setter
public class GoogleUserInfo {
    private String id;
    private String email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;

    public User toUserDomain() {
        return User.builder()
                .email(this.email)
                .password(this.id)
                .authority(Authority.USER)
                .snsType("google")
                .imageUrl(picture)
                .build();
    }
}
