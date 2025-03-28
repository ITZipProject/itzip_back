package darkoverload.itzip.feature.techinfo.ui.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkoverload.itzip.feature.techinfo.domain.entity.Blog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "블로그 응답 정보")
@Builder
public record BlogResponse(
        @Schema(description = "블로그 ID", example = "1")
        Long id,

        @Schema(description = "사용자 이메일", example = "rowing0328@example.com")
        String email,

        @Schema(description = "사용자 닉네임", example = "rowing0328")
        String nickname,

        @Schema(description = "프로필 이미지 URI", example = "http://example.com/profile.jpg")
        @JsonProperty("profile_image_uri")
        String profileImageUri,

        @Schema(description = "블로그 소개", example = "안녕하세요, 블로그 소개입니다.")
        String intro
) {

    public static BlogResponse from(final Blog blog) {
        return builder()
                .id(blog.getId())
                .email(blog.getUser().getEmail())
                .nickname(blog.getUser().getNickname())
                .profileImageUri(blog.getUser().getImageUrl())
                .intro(blog.getIntro())
                .build();
    }

}