package darkoverload.itzip.feature.techinfo.controller.blog.response;

import darkoverload.itzip.feature.techinfo.dto.post.year.YearlyPostDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(
        description = "블로그의 상세 정보를 반환하는 응답 객체로, 블로그 ID, 소유자 정보, 소개글 및 연도별 포스트 수를 포함합니다."
)
@Getter
@Builder
@AllArgsConstructor
public class BlogDetailsResponse {

    @Schema(description = "블로그의 고유 ID", example = "1")
    private Long blogId;

    @Schema(description = "블로그 소유자의 프로필 이미지 경로", example = "")
    private String profileImagePath;

    @Schema(description = "블로그 소유자의 닉네임", example = "hyoseung")
    private String nickname;

    @Schema(description = "블로그 소유자의 이메일 주소", example = "dev.hyoseung@gmail.com")
    private String email;

    @Schema(description = "블로그 소개글", example = "이 블로그는 최신 기술 정보를 공유합니다.")
    private String intro;

    @Schema(description = "연도, 월, 주차별로 포스트 수를 제공하는 리스트 구조로, 연도 -> 월 -> 주차별 포스트 수를 포함합니다.")
    private List<YearlyPostDto> postCountByYear;
}