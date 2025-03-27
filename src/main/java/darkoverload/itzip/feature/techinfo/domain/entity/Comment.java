package darkoverload.itzip.feature.techinfo.domain.entity;

import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@EqualsAndHashCode
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "article_id", nullable = false)
    private String articleId;

    private String content;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_displayed", nullable = false)
    private Boolean displayed;

    protected Comment() {
    }

    public Comment(final UserEntity user, final String articleId, final String content) {
        this.user = user;
        this.articleId = articleId;
        this.content = content;
        this.displayed = true;
    }

    public Comment(final long id, final UserEntity user, final String articleId, final String content, final LocalDateTime createdAt, final LocalDateTime updatedAt, final boolean displayed) {
        this.id = id;
        this.user = user;
        this.articleId = articleId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.displayed = displayed;
    }

    public static Comment create(final UserEntity user, final String articleId, final String content) {
        checkContent(content);
        return new Comment(user, articleId, content);
    }

    private static void checkContent(final String content) {
        if (Objects.isNull(content) || content.isBlank()) {
            throw new RestApiException(CommonExceptionCode.COMMENT_CONTENT_REQUIRED);
        }
    }

    public void updateContent(final String content) {
        checkContent(content);
        this.content = content;
    }

    public void hide() {
        this.displayed = false;
    }

}
