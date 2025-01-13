package darkoverload.itzip.feature.user.entity;

import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false, unique = true)
    private String nickname;

    @Setter
    @Column(nullable = false)
    private String password;

    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String snsType;

    public User convertToDomain(){
        return User.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .imageUrl(this.imageUrl)
                .authority(this.authority)
                .snsType(this.snsType)
                .build();
    }
}
