package darkoverload.itzip.feature.techinfo.model.entity;

import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.global.entity.AuditingFields;

import jakarta.persistence.*;

import lombok.*;

/**
 * PostgreSQL에 저장되는 블로그 정보를 나타내는 엔티티 클래스.
 * 블로그 ID(회원 ID와 동일), 소개글, 소유자(회원)를 포함하고,
 * 생성 및 수정 날짜를 자동으로 관리한다.
 * {@link #convertToDomain()} 메서드를 통해 Blog 도메인 객체로 변환함.
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Table(name = "blogs")
public class BlogEntity extends AuditingFields {

    /**
     * 블로그의 고유 식별자.
     * 회원 ID와 동일하게 사용됨. 블로그는 회원과 1:1 관계를 가짐.
     */
    @Id
    private Long userId;

    /**
     * 블로그 소유자와의 일대일 매핑 관계.
     * {@link UserEntity}와 매핑되어 해당 블로그의 소유자를 나타냄.
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    /**
     * 블로그 소개글.
     * 블로그에 대한 간단한 설명을 담고 있음.
     */
    private String intro;

    /**
     * 블로그의 공개 여부.
     * 블로그가 공개 상태인지 비공개 상태인지를 나타냄. (true: 공개, false: 비공개)
     */
    @Column(name = "is_public")
    private Boolean isPublic;

    /**
     * BlogEntity를 Blog 도메인 객체로 변환함.
     *
     * @return Blog 도메인 객체
     */
    public Blog convertToDomain() {
        return Blog.builder()
                .id(this.userId)
                .user(userEntity.convertToDomain())
                .intro(this.intro)
                .isPublic(this.isPublic)
                .build();
    }
}