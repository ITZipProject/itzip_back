package darkoverload.itzip.feature.user.controller.request;

import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.Authority;
import lombok.Getter;
import lombok.Setter;

/**
 * 깃허브 api 유저 정보 dto
 */
@Getter
@Setter
public class GithubUserInfo {
    private String login;
    private String id;
    private String avatar_url;
    private String email;

    public User toUserDomain() {
        return User.builder()
                .email(this.email)
                .password(this.id)
                .authority(Authority.USER)
                .snsType("github")
                .imageUrl(avatar_url)
                .build();
    }
}
