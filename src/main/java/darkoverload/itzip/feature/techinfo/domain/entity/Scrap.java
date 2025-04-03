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
        name = "scraps",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "article_id"})
        }
)
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Scrap {

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

    protected Scrap() {
    }

    public Scrap(final Long id, final UserEntity user, final String articleId, final LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.articleId = articleId;
        this.createdAt = createdAt;
    }

    public Scrap(final UserEntity user, final String articleId) {
        this(null, user, articleId, null);
    }

    public static Scrap create(final UserEntity user, final String articleId) {
        return new Scrap(user, articleId);
    }

}
