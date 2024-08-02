package darkoverload.itzip.jwt.entity;

import darkoverload.itzip.global.entity.AuditingFields;
import darkoverload.itzip.jwt.domain.Token;
import darkoverload.itzip.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TokenEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Setter
    @Column(nullable = false, length = 300)
    private String accessToken;

    @Column(nullable = false, length = 300)
    private String refreshToken;

    @Column(nullable = false)
    private String grantType;

    public Token convertToDomain() {
        return Token.builder()
                .id(this.id)
                .user(this.userEntity.coverToDomain())
                .accessToken(this.accessToken)
                .refreshToken(this.refreshToken)
                .grantType(this.grantType)
                .build();
    }
}