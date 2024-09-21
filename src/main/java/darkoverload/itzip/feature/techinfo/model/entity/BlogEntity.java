package darkoverload.itzip.feature.techinfo.model.entity;

import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.global.entity.AuditingFields;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 블로그 정보를 데이터베이스에 매핑하는 JPA 엔티티 클래스.
 * 이 클래스는 블로그 ID, 소개글, 소유자(회원 ID)를 포함하며,
 * {@link AuditingFields}를 상속받아 생성 및 수정 날짜를 자동으로 관리한다.
 *
 * 필드:
 * - id: 블로그의 고유 식별자
 * - intro: 블로그 소개글
 * - userId: 블로그 소유자의 ID
 * - isPublic: 블로그의 공개 여부 (true: 공개, false: 비공개)
 *
 * {@link #convertToDomain()} 메서드를 통해 Blog 도메인 객체로 변환할 수 있다.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blogs")
public class BlogEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intro;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "is_public")
    private Boolean isPublic;

    /**
     * BlogEntity를 Blog 도메인 객체로 변환한다.
     *
     * @return Blog 도메인 객체
     */
    public Blog convertToDomain() {
        return Blog.builder()
                .id(this.id)
                .intro(this.intro)
                .userId(this.userId)
                .isPublic(this.isPublic)
                .build();
    }
}