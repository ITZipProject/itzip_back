package darkoverload.itzip.feature.techinfo.controller.post.response;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(
        description = "기술 정보 게시글 기본 응답"
)
@Builder
public record PostResponse(
        @Schema(description = "포스트 ID", example = "66e9a2ed666b0728ada7edbf")
        String postId,

        @Schema(description = "제목", example = "밤하늘 아래, 감정의 여정")
        String title,

        @Schema(description = "작성일", example = "2024-09-18T00:40:29.282")
        String createDate
) {

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .createDate(post.getCreateDate().toString())
                .build();
    }

}
