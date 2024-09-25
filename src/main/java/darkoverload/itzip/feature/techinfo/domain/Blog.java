package darkoverload.itzip.feature.techinfo.domain;

import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;

import jakarta.persistence.Column;

import lombok.*;

/**
 * Blog 클래스는 블로그 정보를 나타내는 도메인 객체.
 * 블로그 ID, 소개글, 소유자(User) 필드를 포함.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    /**
     * 블로그 ID.
     */
    private Long id;

    /**
     * 블로그 소개글.
     */
    @Setter
    private String intro;

    /**
     * 회원 ID.
     */
    private Long userId;

    /**
     * 블로그 공개 여부.
     */
    @Setter
    @Column(name = "is_public")
    private Boolean isPublic;

    /**
     * 회원 가입 시 기본 블로그 생성.
     *
     * @param userId 블로그 소유자의 회원 ID
     */
    public Blog(Long userId) {
        this.userId = userId;
        this.intro = "";
        this.isPublic = true;
    }

    /**
     * Blog 도메인 객체를 BlogEntity로 변환.
     *
     * @return BlogEntity
     */
    public BlogEntity convertToEntity() {
        return BlogEntity.builder()
                .id(this.id)
                .intro(this.intro)
                .userId(this.userId)
                .isPublic(this.isPublic)
                .build();
    }
}