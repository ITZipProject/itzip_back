package darkoverload.itzip.feature.techinfo.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(
        description = "포스트 목록을 반환하는 응답 객체로, 각 포스트의 ID, 제목, 생성일을 포함합니다."
)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListResponse {

    @Schema(description = "포스트의 고유 ID", example = "66e9a2ed666b0728ada7edbf")
    private String postId;

    @Schema(description = "포스트 제목", example = "밤하늘 아래, 감정의 여정")
    private String title;

    @Schema(description = "포스트 생성일", example = "2024-09-18T00:40:29.282")
    private String createDate;
}