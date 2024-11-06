package darkoverload.itzip.feature.techinfo.controller.blog.request;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(
        description = "새로운 블로그 소개글을 포함한 수정 요청 객체"
)
@Getter
@NoArgsConstructor
public class UpdateBlogIntroRequest {

    @Schema(description = "수정된 블로그 소개글", example = "수정된 블로그 소개글입니다.")
    private String intro;
}