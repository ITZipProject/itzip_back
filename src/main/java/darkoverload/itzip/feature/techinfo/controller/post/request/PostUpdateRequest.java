package darkoverload.itzip.feature.techinfo.controller.post.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Schema(
        description = "기술 정보 게시글 수정 요청"
)
public record PostUpdateRequest(
        @Schema(description = "게시글 ID", example = "66e724e50000000000db4e53")
        @NotBlank(message = "포스트 ID는 필수입니다.")
        String postId,

        @Schema(description = "", example = "66ce18d84cb7d0b29ce602f5")
        @NotBlank(message = "카테고리 ID는 필수입니다.")
        String categoryId,

        @Schema(description = "게시글 제목", example = "밤하늘 아래, 감정의 여정")
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @Schema(
                description = "게시글 본문",
                example = "이 세 개의 이미지는 감정의 복잡한 여정을 시각적으로 표현하고 있다. 첫 번째 장면에서는 소녀가 꿈을 향해 하늘로 비상하려 하지만, 현실의 무게에 의해 아래로 끌려 내려가는 모습을 담고 있다. 이는 꿈과 야망을 추구하는 과정에서 마주하는 좌절과 도전을 상징한다. 두 번째 장면에서는 소녀가 밤하늘을 응시하며, 자신의 과거와 잊지 못한 꿈들을 회상하는 장면이 그려진다. 창문 밖으로 보이는 거친 파도는 그녀의 내면에서 일어나는 감정의 소용돌이를 나타낸다. 마지막 장면은 비가 내리는 고요한 거리에서 두 사람이 나란히 걷는 모습을 통해, 서로에게 의지하며 고난을 함께 이겨내는 모습을 보여준다. 이 이미지들은 꿈, 좌절, 그리고 관계 속에서 위로를 찾는 여정을 이야기하며, 인간이 감정을 어떻게 극복하고 성장하는지에 대한 메시지를 전달한다."
        )
        @NotBlank(message = "본문은 필수입니다.")
        String content,

        @Schema(
                description = "썸네일 이미지 URL",
                example = "https://dy1vg9emkijkn.cloudfront.net/techinfo/19cc111f-c8f4-4d64-bd7a-129415e3ffa2.jpg"
        )
        @NotBlank(message = "썸네일 이미지 URL은 필수입니다.")
        String thumbnailImagePath,

        @Schema(
                description = "본문 이미지 URL 목록",
                example = """
                        [
                            "https://dy1vg9emkijkn.cloudfront.net/techinfo/7635bb80-416a-4042-a901-552df46351a8.png",
                            "https://dy1vg9emkijkn.cloudfront.net/techinfo/50d081ca-b2f5-4162-926f-0f061aec2554.png"
                        ]
                        """
        )
        List<String> contentImagePaths
) {
}
