package darkoverload.itzip.feature.techinfo.controller.blog.request;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(
        description = "블로그 업데이트 요청 객체로, 새로운 소개글을 포함합니다."
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdateRequest {

    @Schema(description = "업데이트된 블로그 소개글", example = "수정된 블로그 소개글입니다.")
    private String intro;
}