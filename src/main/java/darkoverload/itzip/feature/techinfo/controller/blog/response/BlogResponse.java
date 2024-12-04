package darkoverload.itzip.feature.techinfo.controller.blog.response;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(
        description = "블로그 기본 정보 응답"
)
@Builder
public record BlogResponse(
        @Schema(
                description = "블로그 소유자 프로필 이미지 URL",
                example = "https://dy1vg9emkijkn.cloudfront.net/profile/19cc111f-c8f4-4d64-bd7a-129415e3ffa2.jpg"
        )
        String profileImagePath,

        @Schema(description = "블로그 소유자의 닉네임", example = "hyoseung")
        String nickname,

        @Schema(description = "블로그 소유자 이메일", example = "dev.hyoseung@gmail.com")
        String email,

        @Schema(description = "블로그 소개", example = "최신 기술 트렌드와 실용적인 개발 팁을 공유하는 기술 블로그입니다.")
        String intro
) {

    public static BlogResponse from(Blog blog) {
        return BlogResponse.builder()
                .profileImagePath(blog.getUser().getImageUrl())
                .nickname(blog.getUser().getNickname())
                .email(blog.getUser().getEmail())
                .intro(blog.getIntro())
                .build();
    }

}
