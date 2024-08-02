package darkoverload.itzip.user.domain;

import darkoverload.itzip.user.entity.Authority;
import darkoverload.itzip.user.entity.UserEntity;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    private String email;

    @Setter
    private String nickname;

    @Setter
    private String password;

    private Authority authority;

    public UserEntity coverToEntity(){
        return UserEntity.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .authority(this.authority)
                .build();
    }
}
