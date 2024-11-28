package darkoverload.itzip.feature.techinfo.controller.blog.response;

import darkoverload.itzip.feature.techinfo.domain.blog.BlogDetails;
import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Schema(
        description = "기술 정보 블로그 상세 정보 응답"
)
@Builder
public record BlogDetailsResponse(
        @Schema(description = "블로그 ID", example = "1")
        Long blogId,

        @Schema(
                description = "블로그 소유자 프로필 이미지 URL",
                example = "https://dy1vg9emkijkn.cloudfront.net/profile/19cc111f-c8f4-4d64-bd7a-129415e3ffa2.jpg"
        )
        String profileImageUrl,

        @Schema(description = "블로그 소유자 닉네임", example = "hyoseung")
        String nickname,

        @Schema(description = "블로그 소유자 이메일 주소", example = "dev.hyoseung@gmail.com")
        String email,

        @Schema(description = "블로그 소개글", example = "최신 기술 트렌드와 실용적인 개발 팁을 공유하는 기술 블로그입니다.")
        String intro,

        @Schema(description = "연도별 포스트 통계")
        List<YearlyPostStats> postCountByYear
) {

    public static BlogDetailsResponse from(BlogDetails blogDetails) {
        return BlogDetailsResponse.builder()
                .blogId(blogDetails.getBlogId())
                .profileImageUrl(blogDetails.getProfileImageUrl())
                .nickname(blogDetails.getNickname())
                .email(blogDetails.getEmail())
                .intro(blogDetails.getIntro())
                .postCountByYear(blogDetails.getYearlyPostCounts())
                .build();
    }

}
