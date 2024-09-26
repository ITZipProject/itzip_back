package darkoverload.itzip.feature.user.domain;

import darkoverload.itzip.feature.user.entity.Authority;
import darkoverload.itzip.feature.user.entity.UserEntity;
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

    @Setter
    private String imageUrl;

    private Authority authority;

    public UserEntity convertToEntity(){
        return UserEntity.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .imageUrl(this.imageUrl)
                .authority(this.authority)
                .build();
    }
}
