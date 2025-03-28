package darkoverload.itzip.feature.techinfo.ui.payload.request.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "블로그 소개글 변경 요청에 대한 Payload")
public record BlogIntroEditRequest(
        @Schema(
                description = "새로운 블로그 소개글",
                example = "새로운 블로그 소개글입니다."
        )
        @NotBlank(message = "블로그 소개글은 필수입니다.")
        String intro
) { }
