package darkoverload.itzip.feature.techinfo.controller.blog.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        description = "기술 정보 블로그 소개글 수정 요청"
)
public record BlogUpdateIntroRequest(
        @Schema(description = "수정할 블로그 소개글", example = "최신 기술 트렌드와 실용적인 개발 팁을 공유하는 기술 블로그입니다.")
        @NotNull
        String intro
) {
}
