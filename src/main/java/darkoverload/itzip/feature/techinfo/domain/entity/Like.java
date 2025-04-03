package darkoverload.itzip.feature.techinfo.domain.entity;

import darkoverload.itzip.feature.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "article_id"})
        }
)
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "article_id", nullable = false)
    private String articleId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Like() {
    }

    public Like(final Long id, final UserEntity user, final String articleId, final LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.articleId = articleId;
        this.createdAt = createdAt;
    }

    public Like(final UserEntity user, final String articleId) {
        this(null, user, articleId, null);
    }

    public static Like create(final UserEntity user, final String articleId) {
        return new Like(user, articleId);
    }

}
