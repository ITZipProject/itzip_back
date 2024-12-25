package darkoverload.itzip.feature.techinfo.domain.blog;

import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

/**
 * 블로그의 상세 정보를 나타내는 도메인 클래스.
 * 블로그 ID, 프로필 이미지 URL, 닉네임, 이메일, 소개글, 연도별 포스트 통계를 포함합니다.
 */
@Getter
public class BlogDetails {

    private final long blogId;
    private final String profileImageUrl;
    private final String nickname;
    private final String email;
    private final String intro;
    private final List<YearlyPostStats> yearlyPostCounts;

    @Builder
    public BlogDetails(long blogId, String profileImageUrl, String nickname, String email, String intro,
                       List<YearlyPostStats> yearlyPostCounts) {
        this.blogId = blogId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.email = email;
        this.intro = intro;
        this.yearlyPostCounts = yearlyPostCounts;
    }

    /**
     * Blog 객체와 연도별 포스트 통계로부터 BlogDetails 생성합니다.
     *
     * @param blog             블로그 정보
     * @param yearlyPostCounts 연도별 포스트 통계
     * @return BlogDetails
     */
    public static BlogDetails from(Blog blog, List<YearlyPostStats> yearlyPostCounts) {
        return BlogDetails.builder()
                .blogId(blog.getId())
                .profileImageUrl(blog.getUser().getImageUrl())
                .nickname(blog.getUser().getNickname())
                .email(blog.getUser().getEmail())
                .intro(blog.getIntro())
                .yearlyPostCounts(yearlyPostCounts)
                .build();
    }

}
