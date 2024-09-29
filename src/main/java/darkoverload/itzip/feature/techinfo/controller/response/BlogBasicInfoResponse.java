package darkoverload.itzip.feature.techinfo.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(
        description = "블로그의 기본 정보를 반환하는 응답 객체로, 블로그 ID, 소유자 정보, 소개글을 포함합니다."
)
@Getter
@Builder
@AllArgsConstructor
public class BlogBasicInfoResponse {

    /**
     * 현재는 사용되지 않으며, 차후에 블로그 관련 기능 개선 시 활용할 예정입니다.
     */
//    @Schema(description = "블로그의 고유 ID", example = "1")
//    private Long blogId;

    @Schema(description = "블로그 소유자의 프로필 이미지 경로", example = "")
    private String profileImagePath;

    @Schema(description = "블로그 소유자의 닉네임", example = "hyoseung")
    private String nickname;

    @Schema(description = "블로그 소유자의 이메일 주소", example = "dev.hyoseung@gmail.com")
    private String email;

    @Schema(description = "블로그의 간단한 소개", example = "이 블로그는 최신 기술 정보를 공유합니다.")
    private String intro;
}