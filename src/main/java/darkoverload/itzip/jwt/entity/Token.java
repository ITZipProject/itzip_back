package darkoverload.itzip.jwt.entity;

import darkoverload.itzip.global.entity.AuditingFields;
import darkoverload.itzip.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Token extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private String grantType;

    // 업데이트 빌더
    public Token update(String accessToken, String refreshToken, String grantType) {
        return Token.builder()
                .id(this.id)
                .user(this.user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType(grantType)
                .build();
    }
}
