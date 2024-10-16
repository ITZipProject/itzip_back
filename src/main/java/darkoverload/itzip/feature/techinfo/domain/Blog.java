package darkoverload.itzip.feature.techinfo.domain;

import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogDetailsResponse;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogSummaryResponse;
import darkoverload.itzip.feature.techinfo.dto.post.year.YearlyPostDto;
import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.user.domain.User;

import lombok.*;

import java.util.List;

/**
 * Blog 클래스는 블로그 정보를 나타내는 도메인 객체.
 * 블로그 ID, 소개글, 소유자(User) 필드를 포함.
 */
@Getter
@Builder
@AllArgsConstructor
public class Blog {

    /**
     * 블로그 ID.
     */
    private Long id;

    /**
     * 블로그 소유자의 정보.
     * {@link User} 객체와 연결되어 블로그의 소유자 정보를 나타냄.
     */
    private User user;

    /**
     * 블로그 소개글.
     */
    @Setter
    private String intro;

    /**
     * 블로그 공개 여부.
     */
    @Setter
    private Boolean isPublic;

    /**
     * 회원 가입 시 기본 블로그 생성.
     *
     * @param user 블로그 소유자의 회원 객체
     */
    public Blog(User user) {
        this.id = user.getId();
        this.user = user;
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
                .userId(this.id)
                .userEntity(this.user.convertToEntity())
                .intro(this.intro)
                .isPublic(this.isPublic)
                .build();
    }

    public BlogSummaryResponse convertToBlogBasicInfoResponse() {
        return BlogSummaryResponse.builder()
                .profileImagePath(this.user.getImageUrl())
                .nickname(this.user.getNickname())
                .email(this.user.getEmail())
                .intro(this.intro)
                .build();
    }

    public BlogDetailsResponse convertToBlogDetailInfoResponse(List<YearlyPostDto> yearlyPostsCount) {
        return BlogDetailsResponse.builder()
                .blogId(this.getId())
                .profileImagePath(this.user.getImageUrl())
                .nickname(this.user.getNickname())
                .email(this.user.getEmail())
                .intro(this.intro)
                .postCountByYear(yearlyPostsCount)
                .build();
    }
}