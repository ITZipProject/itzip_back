package darkoverload.itzip.feature.techinfo.model.entity;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

/**
 * 블로그 정보를 나타내는 엔티티 클래스.
 * AuditingFields 를 상속받아 생성 및 수정 일자를 자동으로 관리합니다.
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blogs")
public class BlogEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String intro;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    /**
     * Blog 로부터 BlogEntity 를 생성합니다.
     *
     * @param blog
     * @return
     */
    public static BlogEntity from(Blog blog) {
        return BlogEntity.builder()
                .id(blog.getId())
                .user(blog.getUser().convertToEntity())
                .intro(blog.getIntro())
                .isPublic(blog.isPublic())
                .build();
    }

    /**
     * BlogEntity 를 Blog 로 변환합니다.
     *
     * @return Blog
     */
    public Blog toModel() {
        return Blog.builder()
                .id(this.id)
                .user(this.user.convertToDomain())
                .intro(this.intro)
                .isPublic(this.isPublic)
                .build();
    }

}
