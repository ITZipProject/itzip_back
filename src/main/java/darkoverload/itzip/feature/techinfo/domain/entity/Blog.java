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
@Table(name = "blogs")
@EntityListeners(AuditingEntityListener.class)
public class Blog {

    private static final String DEFAULT_BLOG_INTRO = "당신만의 블로그 소개글을 작성해주세요.";

    @Id
    private Long id;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false,  unique = true)
    private UserEntity user;

    private String intro;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected Blog() {
    }

    public Blog(final Long id, final UserEntity user, final String intro, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        checkIntro(intro);
        this.id = id;
        this.user = user;
        this.intro = intro;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void checkIntro(final String intro) {
        if (Objects.isNull(intro) || intro.isBlank()) {
            throw new RestApiException(CommonExceptionCode.BLOG_INTRO_REQUIRED);
        }
    }

    public Blog(final UserEntity user, final String intro) {
        this(null, user, intro, null, null);
    }

    public static Blog create(final UserEntity user) {
        return new Blog(user, DEFAULT_BLOG_INTRO);
    }

    public void updateIntro(final String intro) {
        checkIntro(intro);
        this.intro = intro;
    }

}
